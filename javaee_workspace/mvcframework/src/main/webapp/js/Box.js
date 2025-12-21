class Box{
    // new Box
    constructor(container, x, y, width, height,bg,msg) {
        this.container=container;
        this.div = document.createElement("div");
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.bg=bg;
        this.msg=msg;

        this.div.style.position="absolute";
        this.div.style.left=this.x+"px";
        this.div.style.top=this.y+"px";

        this.div.style.width=this.width+"px";
        this.div.style.height=this.height+"px";
        this.div.style.backgroundColor=this.bg;
        this.div.style.borderRadius="5px";
        this.div.style.border="1px solid #cccccc";
        this.div.style.boxSizing="border-box";
        
        //텍스트 반영 
        this.div.innerText=this.msg;

        //화면에 부착 
        this.container.appendChild(this.div);
    }
}
