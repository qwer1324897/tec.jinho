// 앞으로 자주 사용할 코드는 이 파일에 모아놓고 나만의 라이브러리로 만들자.

// 매게변수가 10보다 작은 1의 자리수라면, 앞에 문자 '0' 붙이기

function getZeroIfNumIfSmallThanTen(n) {
    //n = 7
    let result = n;
    if(n<10) {result = "0"+n}
    return result;
}