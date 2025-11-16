$(document).ready(function() {
    $(document).on('keydown', function(e) {
        if (e.key === 'Escape') {
            $('.modal.active').removeClass('active');
        }
    });
});

function goBack() {
    window.location.href = 'main';
}
