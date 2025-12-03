<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .container {
        width: 650px;
        height: 500px;
        margin: auto;
    }
    .aside {
        width: 150px;
        height: 100%;
        background-color: aliceblue;
        float: left;
    }
    .aside input{
        width: 90%;
    }
    .aside button {
        width: 40%;
    }
    .content {
        width: 500px;
        height: 100%;
        background-color: azure;
        float: right;
    }
</style>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>
    // Ã«Â¬Â¸Ã¬ÂÂÃªÂ°Â Ã«Â¡ÂÃ«ÂÂÃ«ÂÂÃ«Â©Â´ Ã«ÂÂ ÃªÂ°ÂÃ¬ÂÂ Ã«Â²ÂÃ­ÂÂ¼Ã¬ÂÂ Ã«ÂÂÃ­ÂÂ´ Ã¬ÂÂ´Ã«Â²Â¤Ã­ÂÂ¸ Ã¬ÂÂ°ÃªÂ²Â°

    $(()=>{     // Ã­ÂÂÃ¬ÂÂ´Ã­ÂÂ Ã­ÂÂ¨Ã¬ÂÂ. ÃªÂ¸Â°Ã¬Â¡Â´ Ã­ÂÂ¨Ã¬ÂÂ Ã¬Â ÂÃ¬ÂÂ function() Ã«Â¥Â¼ ()=> Ã«Â¡Â Ã¬Â¤ÂÃ¬ÂÂ¬Ã¬ÂÂ Ã­ÂÂÃ­ÂÂ
        // Ã«ÂÂÃªÂ¸Â°Ã«Â²ÂÃ­ÂÂ¼(sync)Ã¬ÂÂ Ã­ÂÂ´Ã«Â¦Â­ Ã¬ÂÂ´Ã«Â²Â¤Ã­ÂÂ¸ Ã¬ÂÂ°ÃªÂ²Â°
        $($("form button")[0]).click(()=>{
            // alert("Ã«ÂÂÃªÂ¸Â° Ã«Â°Â©Ã¬ÂÂ Ã¬ÂÂÃ¬Â²Â­ Ã¬ÂÂÃ«ÂÂ");
            $("form").attr({
                action:"/ajax/async_regist.jsp",
                method:"post"
            });
            $("form").submit();
        });

        $($("form button")[1]).click(()=>{
            alert("Ã«Â¹ÂÃ«ÂÂÃªÂ¸Â° Ã«Â°Â©Ã¬ÂÂ Ã¬ÂÂÃ¬Â²Â­ Ã¬ÂÂÃ«ÂÂ");
        });
    });

</script>
</head>
<body>
    <div class="container">
        <div class="aside">
            <form>
                <input type="text" placeholder="Ã¬ÂÂÃ¬ÂÂ´Ã«ÂÂ" name="id">
                <input type="text" placeholder="Ã¬ÂÂ´Ã«Â¦Â" name="name">
                <input type="text" placeholder="Ã¬ÂÂ´Ã«Â©ÂÃ¬ÂÂ¼" name="email">
                <button>sync</button>
                <button>async</button>
            </form>
        </div>
        <div class="content">
        </div>
    </div>
</body>
</html>