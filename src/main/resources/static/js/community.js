function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({
            "parentId": 1,
            "content": "这是一个回复内容",
            "type": 1
        }),
        success: function (response) {
            if (response.code === 200) {
                $("#comment_section").hide();
            }else {
                // 如果是登录异常
                if (response.code === 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=46ce720975d247275cf0&redirect_uri=http://localhost:8887/callback&state=1")
                        window.localStorage.setItem("closable", true);
                    }
                }
                alert(response.message);
            }
        },
        dataType: "json"
    });

    console.log(questionId);
    console.log(commentContent);
}