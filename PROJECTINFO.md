# 프로젝트정보
 > Rest Api 방식
### 버전및구성 정보

* 프레임워크: Springboot 3.4.1
* 언어: java 21
* Data Access: JPA
* 인증방식: JWT 0.12.6
* Database: Maria, h2
* 아키텍처 : Layered(도매인기반)
* 빌드: gradle-8.11.1-bin
* swagger 2.3.0
* 테스트: junit 5.11.4
* queryDSL: 5.0.0

### 적용모듈 정보 및 사용방법
* 스프링 시큐리티
* 다국어: MessageConfig
* 프로퍼티암호화: JasyptConfig
* JPA 스파이쿼리: P6spyConfig


### 응답구조
#### * 성공 (Status Code: 200)
#### * data 하나만 내려가며 구조는 그때그때 다르며 swagger 의 Schema 참고
````
{
  "data": {
    "content": [
      {
        "boardSeq": 1,
        "boardTitle": "우우우우sdfasdfsdf",
        "boardContents": "aaaaaaaaaaaaaa",
        "viewCount": 4,
        "priorityYn": "N",
        "deletedYn": "N",
        "createdBy": null,
        "createdDttm": "2025-02-06 11:01:14.59",
        "modifiedBy": "sil1",
        "modifiedDttm": "2025-02-07 15:41:05.89",
        "commentCount": 2,
        "fileCount": 0,
        "files": null
      }
    ],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 1,
      "sort": {
        "empty": false,
        "sorted": true,
        "unsorted": false
      },
      "offset": 0,
      "paged": true,
      "unpaged": false
    },
    "last": false,
    "totalPages": 5,
    "totalElements": 5,
    "size": 1,
    "number": 0,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "first": true,
    "numberOfElements": 1,
    "empty": false
  }
}
````
#### * 실패 (Status Code: 200이 아닌 모든것)

````
{
  "errorCode": "1006",
  "message": "필드 유효성 검증 실패입니다..",
  "errors": [
    {
      "errorField": "priorityYn",
      "errorMessage": "[string]허용되지 않은 값입니다."
    }
  ]
}
````

#### * 로컬적용
> * [SWAGGER](http://localhost:8080/swagger-ui/index.html)
> * H2 db 사용가능
> * run configuration 에서 local주입해서 사용