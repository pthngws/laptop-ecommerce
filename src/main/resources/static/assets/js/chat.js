let SENDERID = null
let RECEIVERID = null;
let isAdmin = false;
let stompClient = null;
let lastMessageDate = null;
// Fetch user từ server
var userId = document.getElementById("userId").innerText; // Giá trị là chuỗi
SENDERID = parseInt(userId, 10); // Chuyển đổi thành số nguyên (int)
const token = localStorage.getItem('jwtToken');

if (token) {
    // Sử dụng jwt-decode để giải mã
    const decodedToken = jwt_decode(token); // jwt-decode trả về object chứa payload

    // Lấy thông tin từ payload
    SENDERID = decodedToken.userid; // Gán USERID từ token
    const role = decodedToken.role; // Gán role từ token

    // Kiểm tra role để xác định isAdmin
    if (role !== "Customer") {
        SENDERID = 1; // Admin được mặc định có USERID là 1
    }
}

isAdmin = SENDERID === 1;

function toggleChatPopup() {
    const chatPopup = $("#chat-popup");
    const chatButton = $(".chat-button");
    chatPopup.css("display", chatPopup.css("display") === "block" ? "none" : "block");
    if (chatPopup.css("display") === "block")

        if (isAdmin) {
            $("#page-title").text("Tin Nhắn");
            loadCustomerList();
            $("#customer-list").show();
            $("#chat-room").hide();
        } else {
            openChat(1,"Chăm sóc khách hàng");
        }

}

async function loadCustomerList() {
    try {
        // Gửi yêu cầu lấy danh sách khách hàng
        const customerData = await $.ajax({
            url: '/getCustomerList',
            type: 'GET'
        });

        // Lấy phần tử danh sách
        const customerListItems = $("#customer-list-items");

        // Xóa nội dung cũ
        customerListItems.empty();

        // Đảo ngược và thêm dữ liệu khách hàng vào danh sách
        customerData.reverse().forEach(customer => {
            const listItem = $("<li>")
                .text(`${customer.name}`) // Hiển thị tên
                .click(() => openChat(customer.userID, customer.name)); // Mở chat khi click
            customerListItems.append(listItem);
        });
    } catch (error) {
        console.error("Error loading customer list:", error);
    }
}



function openChat(customerId,customerName) {
    RECEIVERID = customerId;
    $("#page-title").text(customerName);
    $("#customer-list").hide();
    $("#chat-room").show();
    connect();
    loadMessages();

}

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/public', function () {
            loadMessages();
        });
    }, function (error) {
        console.error('WebSocket connection error:', error);
    });
}

function loadMessages() {
    $.ajax({
        url: '/getMessages',
        type: 'GET',
        data: { senderId: SENDERID, receiverId: RECEIVERID },
        success: function (messages) {
            $("#chat-box").empty();  // Xóa nội dung cũ trong hộp chat
            if (messages.length === 0) {
                // Nếu không có tin nhắn, hiển thị tin nhắn mặc định
                if (!isAdmin) {
                    showMessage({
                        contentMessage: "LaptopT4 đang có nhiều chương trình khuyến mãi và ưu đãi hấp dẫn. Anh/Chị có thể nhắn tin để được tư vấn chi tiết.",
                        senderID: 1,  // Admin mặc định có ID là 1
                        timestamp: new Date().toISOString()  // Thời gian hiện tại
                    });
                }
            } else {
                // Nếu có tin nhắn, hiển thị tất cả tin nhắn
                messages.forEach(showMessage);
            }
        },
        error: function (error) {
            console.error('Error loading messages:', error);
        }
    });
}


function showMessage(message) {
    const chatBox = $("#chat-box");
    const messageElement = $("<div>").addClass("message");

    const sentTime = new Date(message.timestamp);
    const formattedTime = sentTime.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    const messageDate = sentTime.toLocaleDateString();

    if (lastMessageDate !== messageDate) {
        const dateSeparator = $("<div>")
            .addClass("date-separator")
            .html(`<small>${messageDate}</small>`);
        chatBox.append(dateSeparator);
        lastMessageDate = messageDate;
    }

    const messageBubble = $("<div>")
        .addClass("message-bubble")
        .html(`<span>${message.contentMessage}</span> <div class="message-time">${formattedTime}</div>`);

    if (message.senderID === SENDERID) {
        messageElement.addClass("sender");
    } else {
        messageElement.addClass("receiver");
    }

    messageElement.append(messageBubble);
    chatBox.append(messageElement);
    chatBox.scrollTop(chatBox[0].scrollHeight);
}
// Lắng nghe sự kiện khi người dùng nhấn phím trong ô nhập liệu
document.getElementById('message-input').addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {  // Kiểm tra nếu người dùng nhấn Enter
        event.preventDefault();  // Ngừng hành động mặc định (tránh việc form gửi lại trang)
        sendMessage();           // Gọi hàm gửi tin nhắn
    }
});

// Hàm gửi tin nhắn
function sendMessage() {
    const input = $("#message-input");
    const messageContent = input.val().trim();

    if (messageContent && stompClient && SENDERID && RECEIVERID) {
        const message = {
            contentMessage: messageContent,
            senderID: SENDERID,
            receiverID: RECEIVERID,
            sentTime: new Date().toISOString()
        };

        // Gửi tin nhắn qua WebSocket
        stompClient.send("/app/sendMessage", {}, JSON.stringify(message));

        // Xóa nội dung ô nhập liệu
        input.val("");
    }
}