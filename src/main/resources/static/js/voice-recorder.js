function openChat(chatId) {
    let $chatData = $('#chat-' + chatId);
    $('#chatTitle').text($chatData.data('title'));
    $('#chatContainer').html($chatData.html());
    $('#listView').hide();
    $('#chatView').addClass('active');
}

function closeChat() {
    $('#listView').show();
    $('#chatView').removeClass('active');
}
