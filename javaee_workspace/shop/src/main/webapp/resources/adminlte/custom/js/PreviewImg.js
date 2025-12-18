

// 상품 이미지를 클릭하면 해당 상품 이미지를 화면에 미리보기로 띄워주는 기능 구현을 위한 클래스 성능
class PreviewImg {
	constructor(container, file, src, width, height) {
		this.container = container;
		this.file = file;
		this.src = src;
		this.width = width;
		this.height = height;
		
		this.wrapper = document.createElement("div");
		this.header = document.createElement("div");
		this.img = document.createElement("img");
		this.img.src=this.src;
		
		// style
		this.img.style.width = this.width + "px";
		this.img.style.height = this.height + "px";
		
		this.wrapper.style.width = (this.width+10) + "px";
		this.wrapper.style.height = (this.height+30) + "px";
		this.wrapper.style.display = "inline-block";	// 너비, 높이 등의 block 요소의 특징을 유지하면서 다른 요소와 수평으로 공존하게 하기 위함.
		this.wrapper.style.margin = 5+"px";
		this.wrapper.style.border = "1px solid aliceblue";
		this.wrapper.style.borderRadius = "5px";
		this.wrapper.style.textAlign = "center";
		
		this.header.innerHTML = "<a href='#'>X</a>";
		this.header.style.textAlign="right";
		
		// 조립
		this.wrapper.appendChild(this.header);
		this.wrapper.appendChild(this.img);
		this.container.appendChild(this.wrapper);
		
	}
}

/**
 * 
 */