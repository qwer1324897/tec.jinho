<%@page import="com.ch.shop.dto.TopCategory"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>AdminLTE 3 | Dashboard</title>

	<%@ include file="../inc/head_link.jsp" %>
	
</head>
<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">

  <!-- Preloader -->

	<%@ include file="../inc/preloader.jsp" %>

  <!-- Navbar -->
  
  	<%@ include file="../inc/navbar.jsp" %>
  
  <!-- /.navbar -->

  <!-- Main Sidebar Container -->
  
  <%@ include file="../inc/sidebar.jsp" %>
  
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0">상품등록</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">상품관리</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
      
        <!-- Small boxes (Stat box) -->
        <div class="row">
			<div class="col-md-12">
			
	            <div class="card card-info">
	              <div class="card-header">
	                <h3 class="card-title">상품 등록</h3>
	              </div>
	              <!-- /.card-header -->
	              <!-- form start -->
	              <form>
	                <div class="card-body">
	                
	                	<div class="form-group row">
							<div class="col-md-6">
		                        <select class="form-control" name="topcategory"></select>
	                      	</div>
                      
							<div class="col-md-6">
	                        	<select class="form-control" name="subcategory"></select>
                      		</div>	       
                      	</div>                    	                
	                
	                  <div class="form-group">
	                    <input type="text" class="form-control" name="price" placeholder="상품명">
	                  </div>
	                  
	                  <div class="form-group">
	                    <input type="text" class="form-control" name="brand" placeholder="브랜드">
	                  </div>
	                  
	                  <div class="form-group">
	                    <input type="number" class="form-control" name="price" placeholder="가격(숫자로 입력)">
	                  </div>	   
	                  
	                  <div class="form-group">
	                    <input type="number" class="form-control" name="discount" placeholder="할인가(숫자로 입력)">
	                  </div>	                                 

	                  <div class="form-group">
	                    <input type="text" class="form-control" name="introduce" placeholder="간단소개">
	                  </div>

	                  <div class="form-group">
	                    <textarea id="summernote" class="form-control" name="detail" placeholder="상품상세"></textarea>
	                  </div>

	                  <div class="form-group">
	                    <label for="exampleInputFile">File input</label>
	                    <div class="input-group">
	                      <div class="custom-file">
	                        <input type="file" class="custom-file-input" id="product-img" multiple>
	                        <label class="custom-file-label" for="exampleInputFile">Choose file</label>
	                      </div>
	                      <div class="input-group-append">
	                        <span class="input-group-text">Upload</span>
	                      </div>
	                    </div>
	                  </div>
	                  
	                  <div class="form-group row">
	          			<div class="col-md-12" id="product-preview"></div>
	                  </div>
	                  
	                </div>
	                <!-- /.card-body -->
	
	                <div class="card-footer">
	                  <button type="submit" class="btn btn-info">Submit</button>
	                </div>
	              </form>
	            </div>
	            <!-- /.card -->

			</div>
        </div>
        <!-- /.row -->
        
		<!-- 메인 컨텐츠 끝 -->
        
      </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

	<%@ include file="../inc/footer.jsp" %>

  <!-- Control Sidebar -->

	<%@ include file="../inc/control_sidebar.jsp" %>

  <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->

	<%@ include file="../inc/footer_link.jsp" %>
	<script src="/static/adminlte/custom/js/PreviewImg.js"></script>
	<script>

		// 상위카테고리, 하위카테고리 전부를 처리하는 함수. 따라서 호출 할 때 상위카테고리인지 하위카테고리인지 구분해야하므로 
		// category 파라미터를 사용해 printCategory("topcategory", list) 이런식으로 구분. 
		function printCategory(category, list) {
			let tag = "<option value='0'>카테고리 선택</option>";
			for(let i = 0 ; i < list.length ; i++) {
				if(category == "topcategory") {
					tag += "<option value='"+ list[i].topcategory_id +"'>" + list[i].topname + "</option>"; 	// 위에 html 의 subcategory <option></option> 태그에 누적					
				} else{
					tag += "<option value='"+ list[i].subcategory_id +"'>" + list[i].subname + "</option>"; 	// 위에 html 의 subcategory <option></option> 태그에 누적
				}
			}
			$("select[name='"+ category +"']").html(tag);
		}
		
		function getTopCategory() {
			$.ajax({	  // Jquery 의 비동기 통신 문법
				url:"/admin/topcategory/list",
				method:"GET",
				success: function(result,status,xhr) {
					// 서버에서 응답한 정보인 result 를 이용하여 select 박스에 출력
					printCategory("topcategory", result);
				},
				error:function(xhr,status,err) {
					
				}
				
			});
		}
		
		
		function getSubCategory() {
			$.ajax({     // Jquery 의 비동기 통신 문법
				url:"/admin/subcategory/list?topcategory_id=" +$("select[name='topcategory']").val() ,
				method:"GET", 
				
				// 요청 후 서버에서 응답이 도착했을 때 동작할 속성 및 콜백함수 정의
				// result: 서버에 보낸 데이터, status 서버의 상태, xhr XMLHttpRequest 객체
				success:function(result,status,xhr) {	// 서버의 응답이 200번대이면 success 에 명시된 익명함수가 동작하고,
					// 스프링에서 문자열을 전송 시 content-type 을 json 으로 전송했기 때문에, 클라이언트 측인 자바스크립트에서 별도로 JSON.parse() 를 할 필요 없게 되었음(딸깍으로 끝)
					console.log(result[0].subname);
					printCategory("subcategory", result);    // 자바스크립트의 객체로 이루어진 배열 전달
				},
				error:function(xhr,status,err){		// 서버의 응답이 300번대 이상(문제가 있을 경우)이면 이 error에 명시된 익명함수가 동작.
				}
			});
		}
		
		// 자바스크립트의 이벤트 객체 정보 안에 포함된 유사 배열 객체를 이용하여 화면에 유저가 업로드한 미리보기 이미지를 구현.
		function preview(imgList) {
			for(let i = 0 ; i<imgList.length ; i++) {
				let reader = new FileReader();		// 순수 자바스크립트가 아닌 브라우저 API 등장. 파일을 읽어주는 API
				
				// 이미지가 아래의 메서드에서 다 읽혀지면 호출되는 콜백함수 처리
				// 또한 읽혀진 파일 정보는 매개변수로 전달되어진다.
				reader.onload=function(e){
					// 우리가 만든 클래스로부터 인스턴스 생성하기
					console.log(e.target);
					let pImg = new PreviewImg(document.getElementById("product-preview"), imgList[i], e.target.result, 100, 100);
				}
				reader.readAsDataURL(imgList[i]);	// 지정한 파일 객체를 읽어들이는 메서드
			}
		}
	
		$(()=> {
			$("#summernote").summernote();
			
			// 상위 카테고리의 select 상자의 값을 변경할 때, 비동기방식으로 즉 새로고침 없이 하위 카테고리를 출력해야한다. 
			// 지금까지는 js의 순수 코드를 이용하여 비동기 통신을 수행했지만, 이번에는 Jquery가 지원하는 비동기 통신 방법을 사용.
			getTopCategory();
			
			
			$("select[name='topcategory']").change(()=>{
				getSubCategory();
			});
			
			// 유저가 이미지 컴포넌트에서 이미지 변경할 때 처리하는 이벤트 연결
			$("#product-img").change((e)=>{
				// 사용자가 이미지 컴포넌트를 통해 이미지를 선택하면, 선택한 개수에 따라 배열로 모아서 반환해주는데,
				// 이 때 이미지 정보가 담겨있는 FileList 라는 객체는, 순수 자바스크립트 언어에서 지원하는 객체가 아니라
				// 크롬과 같은 브라우저 API 를 제공하는 객체이다. 특히 FileList(readOnly)는 읽기 전용이어서 개발자가 수정, 삭제 불가능.
				console.log("이미지 바꿈?", e.target.files);
				preview(e.target.files);
			});
			
			
		});
	</script>
	
</body>
</html>



