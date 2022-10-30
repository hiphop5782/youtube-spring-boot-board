<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>

<div class="container-fluid" id="reply-app">

	<!-- 제목 -->
	<div class="row mt-4">
		<div class="col-md-10 offset-md-1">
			<h1>${board.title}</h1>
			<h2 class="text-secondary">${board.writer}</h2>
		</div>
	</div>
	
	<!-- 게시글 정보표시자리 -->
	<div class="row mt-4">
		<div class="col-md-10 offset-md-1">
			<div class="row">
				<div class="col-6 text-start">
					<i class="fa-solid fa-eye"></i> <span class="ms-1">${board.readcount}</span>
					<span class="ms-4 text-secondary"><fmt:formatDate value="${board.writeTime}" pattern="y년 M월 d일 E a h시 m분 s초"/></span>
				</div>
				<div class="col-6 text-end">
					<i class="ms-2 fa-regular fa-bookmark"></i>
					<i class="ms-2 fa-solid fa-share-nodes"></i>
					<i class="ms-2 fa-regular fa-share-from-square"></i>
				</div>
			</div>
			
			<hr>
		</div>
	</div>
	
	
	
	<!-- 게시글 내용 -->
	<div class="row mt-4" style="min-height:350px;">
		<div class="col-md-10 offset-md-1">
			<!-- 에디터를 적용했으므로 pre 제거 -->
			${board.content}
		</div>
	</div>
	
	<!-- 버튼 영역 -->
	<div class="row mt-4">
		<div class="col-md-10 offset-md-1 text-end">
			<hr>
			
			<!-- 글쓰기와 다르게 답글쓰기는 계산을 위해 원본글의 번호를 전달해야함 -->
			<a href="write" class="btn btn-primary">글쓰기</a>
			<a href="write?no=${board.no}" class="btn btn-success">답글쓰기</a>
			
			<!-- 
				수정과 삭제가 password 페이지를 거쳐서 갈 수 있도록 링크 수정
				- 주소는 /password/edit 또는 delete/번호 형태로 경로 변수 처리 
			-->
			<a href="password/edit/${board.no}" class="btn btn-warning">수정</a>
			<a href="password/delete/${board.no}" class="btn btn-danger">삭제</a>
			<a href="./" class="btn btn-dark">목록</a>
		</div>
	</div>
	
	<!-- 댓글 표시 영역 -->
	<div class="row mt-4">
		<div class="col-md-10 offset-md-1">
			{{replyCount}}개의 댓글이 있습니다
		</div>
	</div>
	
	<form method="post" class="reply-form" @submit.prevent="insertReply">
	<div class="row mt-4">
		<div class="col-md-10 offset-md-1">
			<input name="writer" class="form-control" placeholder="댓글 작성자" v-model="reply.writer">
		</div>
	</div>
	<div class="row mt-4">
		<div class="col-md-10 offset-md-1">
			<textarea name="content" class="form-control" rows="4" style="resize: none;" placeholder="댓글 내용" v-model="reply.content"></textarea>
		</div>
	</div>
	<div class="row mt-4">
		<div class="col-md-10 offset-md-1">
			<button type="submit" class="btn btn-primary w-100">등록</button>
		</div>
	</div>
	</form>
	
	<div class="row mt-4" v-for="(reply, index) in replyList" :key="reply.no">
		<div class="col-md-10 offset-md-1">
			<hr>
			<h5 class="text-dark">{{reply.writer}}</h5>
			<h6 class="text-secondary">
				{{convertTime(reply.writeTime)}}
			</h6>
			<pre class="mt-3" style="min-height:75px;">{{reply.content}}</pre>
		</div>
	</div>
</div>

<script src="https://unpkg.com/vue@next"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/locale/ko.min.js"></script>
<script>
	Vue.createApp({
		data(){
			return {
				reply:{
					no:${board.no},//게시글 번호(필수)
					writer:"",//댓글 작성자(필수)
					content:"",//댓글 내용(필수)
				},
				replyList:[],//댓글 목록
				replyCount:0,//댓글 개수
			};
		},
		methods:{
			async loadReplyList(){
				//댓글 목록
				const resp1 = await axios.get("${pageContext.request.contextPath}/reply/list/"+this.reply.no);
				this.replyList = resp1.data;
				
				//댓글 개수(페이징을 할 경우 따로 구해야 올바른 개수가 나옴)
				const resp2 = await axios.get("${pageContext.request.contextPath}/reply/count/"+this.reply.no);
				this.replyCount = resp2.data;
			},
			async insertReply(){
				await axios.post("${pageContext.request.contextPath}/reply/insert", this.reply);
				this.reply.writer = "";
				this.reply.content = "";
				this.loadReplyList();
			},
			convertTime(time){
				return moment(time).fromNow();
			}
		},
		mounted(){
			this.loadReplyList();
		},
	}).mount("#reply-app");
</script>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>


