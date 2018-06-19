프래임워크 : spring boot 2.0.3
사용 언어 : JAVA 8
화면 구성 : 부트스트랩, jquery, handlebars(ver 0.3.0)
테스트 : JUnit Test
프로젝트 빌드 : gradle
실행방법 : ShorteningApplication 파일 우 클릭 후 실행


시나리오
1. 같은 URL 은 같은 ShortUrl 을 return 한다
2. ShortUrl 은 8 글자의 길이를 갖는다
3. domain/ShortUrl 클릭시 원본 Url 페이지로 redirect 한다
4. 생성되지 않은 ShortUrl 로 접근시 Error 페이지 이동
5. Error 페이지에서 main 화면으로 이동 링크 제공


알고리즘(base62 암호화 알고리즘 이용)
1. 62개의 대소문자 및 숫자로 이루어진 배열 생성
2. 랜덤한 숫자 생성후 62의 숫자로 나누어 그 몫으로 앞서 생성한 배열 index 
3. 기존에 생성한 Key-Value 가 있는지 체크 후 있으면 재생성 없으면 생성된 랜덤 Key Return
4. 생성된 Key 을 ShortUrl 로 사용 