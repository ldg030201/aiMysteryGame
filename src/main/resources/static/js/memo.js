let nowMemoId = null;

function openMemo(memoId) {
    nowMemoId = memoId;
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
    nowMemoId = null;
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

function saveMemo(userId) {
    $.ajax ('/memo/save', {
        type: 'post'
        , data: {
            memoId: nowMemoId
            , userId
            , title: $('#detailTitle').val()
            , memo: $('#detailContent').val()
        }
        , success: () => {
            alert('메모가 저장되었습니다!');
            location.reload();
        }
        , error: () => {
            alert("저장 도중 오류가 발생하였습니다.");
        }
    });
}

$(document).ready(function () {
    $('#detailContent').on('input', function () {
        $(this).css('height', 'auto').css('height', this.scrollHeight + 'px');
    });
});
