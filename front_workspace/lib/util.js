// document.cookie="name=jin; max-age=2400";
// document.cookie="email=naver; max-age=2400";

/*
쿠키에 들어있는 데이터 중 원하는 데이터를 접근하기 위해 key를 매개변수로 호출하면 된다.
ex) "name=태호; email=google" 과 같이 쿠키값이 구성되어 있을 경우
    만약 이름을 가져오고 싶다면, key 값에 

*/

function getCookie(key) {
    let arr = document.cookie.split(";");   // ["name=jin", "email=naver"]
    for (let i = 0; i<arr.length; i++) {
        let kv = arr[i].split("=");
        if(kv[0]==key) {
            return kv[1];
        }
    }
}
async function getCookie2(key) {
    const cookie = await cookieStore.get(key);
    if (cookie) {
        return cookie.value;
    } else {
        return null;
    }
}