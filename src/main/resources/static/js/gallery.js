let currentIndex = 0;
let $items = null;
let totalImages = 0;

$(document).ready(function() {
    $items = $('.gallery-item');
    totalImages = $items.length;

    $(document).on('keydown', function(e) {
        if ($('#imageModal').hasClass('active')) {
            if (e.key === 'ArrowLeft') prevImage();
            if (e.key === 'ArrowRight') nextImage();
        }
    });
});

function openImage(index) {
    currentIndex = index;
    updateModal();
    $('#imageModal').addClass('active');
}

function updateModal() {
    let $item = $items.eq(currentIndex);
    let src = $item.find('img').attr('src');
    let title = $item.data('title');
    $('#modalImage').attr('src', src);
    $('#imageInfo').text(title + ' (' + (currentIndex + 1) + '/' + totalImages + ')');
}

function closeImageModal() {
    $('#imageModal').removeClass('active');
}

function closeModalOnBackground(e) {
    if (e.target.id === 'imageModal') closeImageModal();
}

function prevImage() {
    currentIndex = (currentIndex - 1 + totalImages) % totalImages;
    updateModal();
}

function nextImage() {
    currentIndex = (currentIndex + 1) % totalImages;
    updateModal();
}
