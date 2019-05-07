
// 시작할 때 실행
$('document').ready(pageInit());

let currentUser = null; // 현재 유저
let currentPost = null; // 현재 게시글
let postCount = 0; // 게시글 수

let isNewPost = false; // add, update 구분

// 초기화
function pageInit() {

    getAllPosts();
    
}
// 게시글 모두 받아옥
async function getAllPosts(){
    try {
        let response = await $.get('/getAllPosts');

        for (let i = 0; i < response.data.length; i++) {
            // 글 데이터 하나씩 담기
            currentPost = response.data[i];
            // 하나씩 리스트로 보여주기
            $('#recent-post-list').prepend(`
                <li id="postList${currentPost.id}"><div onclick="getPost(${currentPost.id})">${currentPost.title}</div></li>
            `);
            postCount += 1;
        }
        // 최근 글 보여주기
        viewPost(currentPost);

    } catch (e) {
    }

    // 유저 초기화하기
    getUser(1);
}

// 게시글 받아오기
async function getPost(postId) {
    try {
        let response = await $.get(`/getPost/${postId}`);

        currentPost = response.data;
        viewPost(currentPost);

    } catch (e) {
        alert("글을 불러올 수 없습니다.");
    }
}

// 유저 받아오기
async function getUser(userId) {
    // 유저 정보 초기화하기
    try {
        let response = await $.get(`/getUser/${userId}`);
        currentUser = response.data;

        viewUser(currentUser);

    } catch (e) {
        alert("유저를 불러올 수 없습니다.");
    }
}

// 게시글 수정
async function updatePost(button) {
    if($(button).text() === '수정'){
        $('#display-post-title').html(`<input value="${currentPost.title}" id="input-title" type="text">`);
        $('#display-post-content').html(`<textarea style="position: relative; width: 100%; height: 100%" id="input-content" type="text">${currentPost.content}</textarea>`);

        $(button).text('확인');
        $(button).next().text('취소');

    } else if($(button).text() === '확인'){
        // 새 글 쓰기
        if(isNewPost){
            try{
                let addPost = {
                    userId:currentUser.id,
                    title:$('#input-title').val().trim(),
                    content:$('#input-content').val().trim()
                }
                let response = await $.ajax({
                    url: '/addPost',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(addPost)
                });
                currentPost = response.data;
                isNewPost = false;
                reloadPage();
            } catch (e) {
                alert("글을 수정할 수 없습니다.");
            }
        }
        // 글 수정하기
        else {
            try{
                let updatePost = {
                    id:currentPost.id,
                    userId:currentUser.id,
                    title:$('#input-title').val().trim(),
                    content:$('#input-content').val().trim()
                }
                let response = await $.ajax({
                    url: '/updatePost',
                    type: 'PUT',
                    contentType: 'application/json',
                    data: JSON.stringify(updatePost)
                });
                currentPost = response.data;
                viewPost(currentPost);

            } catch (e) {
                alert("글을 수정할 수 없습니다.");
            }
        }
        $(button).text('수정');
        $(button).next().text('삭제');
    }
}

// 게시글 삭제
async function deletePost(button) {
    if($(button).text() === '삭제'){
        try {
            let response = await $.ajax({
                url: `/deletePost/${currentPost.id}`,
                type: 'DELETE'
            });

            if (response.data === true){
                reloadPage();
            }
            else{
                alert('삭제하지 못했습니다');
            }
        } catch (e) {
            console.log((JSON.stringify(e)));
        }
    } else if($(button).text() === '취소'){
        $(button).text('삭제');
        $(button).prev().text('수정');
        viewPost(currentPost);
        isNewPost = false;
    }

}

// 게시글 추가
function newPost() {
    isNewPost = true;
    if ($('#updateButton').text() === '수정') {
        $('#display-post-title').html(`<input id="input-title" type="text">`);
        $('#display-post-content').html(`<textarea style="position: relative; width: 100%; height: 100%" id="input-content" type="text"></textarea>`);

        $('#updateButton').text('확인');
        $('#deleteButton').text('취소');
    } else if($('#updateButton').text() === '확인'){
        alert("글을 수정 중입니다.");
    }
}

/*
VIEW
*/
// 데이터를 받으면 게시글로 보여줌
function viewPost(post) {
    $('#display-post-title').html(post.title);
    $('#display-post-username').html(post.username);
    $('#display-post-createdTime').html(post.created);
    $('#display-post-content').html(post.content);
}

// 데이터를 받으면 유저로 보여줌
function viewUser(user) {
    $('#display-user-id').html(user.id);
    $('#display-user-name').html(user.name);
    $('#display-user-created').html(user.created);
    $('#display-user-postCount').html('게시글 수 : ' + postCount);
}

// 새로 고침 해야할 것들만 모음.
async function reloadPage() {
    postCount = 0;
    $('#recent-post-list').html('');
    try {
        let response = await $.get('/getAllPosts');

        for (let i = 0; i < response.data.length; i++) {
            // 글 데이터 하나씩 담기
            currentPost = response.data[i];
            // 하나씩 리스트로 보여주기
            $('#recent-post-list').prepend(`
            <li id="postList${currentPost.id}"><div onclick="getPost(${currentPost.id})">${currentPost.title}</div></li>
        `);
            postCount += 1;
        }
    } catch (e) {

    }
    viewPost(currentPost);
    getUser(currentUser.id);
}