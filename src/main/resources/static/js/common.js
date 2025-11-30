$(document).ready(function() {
    $(document).on('keydown', function(e) {
        if (e.key === 'Escape') {
            $('.modal.active').removeClass('active');
        }
    });
});

function goBack(userId) {
    window.location.href = 'main?userId=' + userId;
}
