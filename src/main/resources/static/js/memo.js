function openMemo(memoId) {
    let $memo = $('#memo-' + memoId);
    $('#detailTitle').val($memo.data('title'));
    $('#detailContent').val($memo.data('content'));
    $('#detailDate').text('마지막 수정: ' + $memo.data('date'));
    $('#listView').hide();
    $('#detailView').addClass('active');

    let $content = $('#detailContent');
    $content.css('height', 'auto').css('height', $content[0].scrollHeight + 'px');
}

function createNewMemo() {
    $('#detailTitle').val('');
    $('#detailContent').val('');
    $('#detailDate').text('');
    $('#listView').hide();
    $('#detailView').addClass('active');
    $('#detailTitle').focus();
}

function closeMemo() {
    $('#listView').show();
    $('#detailView').removeClass('active');
}

function saveMemo() {
    alert('메모가 저장되었습니다!');
    //TODO-LDG 메모장 데이터 저장
    closeMemo();
}

$(document).ready(function() {
    $('#detailContent').on('input', function() {
        $(this).css('height', 'auto').css('height', this.scrollHeight + 'px');
    });
});
