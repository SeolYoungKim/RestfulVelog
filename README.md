# RestfulVelog

현재 공부 중입니다.

폭풍 구글링 + 호돌맨님 강의로 이루어진 코드입니다.

이를 통해, 다음의 내용을 학습할 수 있었습니다.

1. MockTest 
   - Controller 단위 테스트
   - @WebMvcTest 애노테이션
   - jsonpath
   - BDDMockito
   - MockMvc


2. 검증 관련 팁
   - Debugging 시 Evaluate 기능 활용


3. Entity를 Controller 단에서 직접 취급하지 말 것
   - 순환 참조가 일어날 확률이 높아진다.
   - toString, lombok, json이 순환 참조의 주된 원인이다.
   - 요청에 대한 DTO를 만들 것
   - 응답에 대한 DTO를 만들 것
   - 이렇게만 해도 에러 날 확률이 감소한다.


4. QueryDsl을 이용한 Paging 처리
   - limit
   - offset
   - orderBy


5. JpaRepository<Entity, Long>
   - deleteAllInBatch

    
6. DataBase
   - DB schema 생성 옵션
     - create, create-drop, update, validate, none
   - SQL
     - 커서
     - 연관관계 매핑


7. Build 방식 간의 차이 (Gradle vs IntelliJ)
   - Incremental build (IntelliJ) 
     - 변경된 부분만 빌드
     - 빠른 빌드가 가능
     - 삭제됐던 파일이 그대로 포함된 상태로 빌드가 완료될 수 있다는 위험이 있음
   - Gradle
     - 자바 플러그인 태스크에 등록된 모든 것을 빌드
     - 더 많은 것을 지원한다.
     - Query DSL 이용 시, Gradle을 통한 빌드를 권장한다. (허니레몬 님 블로그 참고)


8. Query DSL
   - limit : 몇 개를 보여줄건지
   - offset : 시작 지점 (0부터 시작함. 0, 1, 2, 3...)
     - page = 1 -> offset = 0
       - 0부터 N 까지 limit 갯수에 맞게 데이터를 찾아옴
   - fetch
     - fetch() : 리스트로 결과 반환. 없으면 빈 리스트
     - fetchOne() : 단건 조회 시 사용. 없으면 null 반환. 값이 2개 이상이면 NonUniqueResultException
     - fetchFirst() : 처음 한 건 조회 (== .limit(1).fetchOne())
     - fetchResults() : 페이징을 위해 사용될 수 있음. 조회한 리스트 + 전체 개수를 포함한 total contents 반환. 
       - count 쿼리가 추가로 실행됨.
     - fetchCounts() : count 쿼리를 날린다. 개수를 조회하고, long type을 반환한다.
   - sort
     - desc() : 내림차순 정렬. (ex : .orderBy(article.id.desc()))
     - asc() : 오름차순 정렬. (ex : .orderBy(article.id.asc())) 
     - 여러 조건으로 정렬 : (ex : .orderBy(article.id.desc(), article.title.asc()))
     - null이 있을 때
       - null이 가장 뒤에 나오게 하려면 `nullsLast()` (ex : .orderBy(article.id.asc().nullsLast())) 
       - null이 가장 앞 나오게 하려면 `nullsFirst()` (ex : .orderBy(article.id.asc().nullsFirst())) 


9. API 문서





### 참고자료

---

[JPA N+1 문제 및 해결방안](https://jojoldu.tistory.com/165)

[[querydsl] queryDsl 기본 문법 정리 - fetch, sort, paging, aggregation](https://devkingdom.tistory.com/243)

[[JPA] QueryDSL Fetch & FetchJoin](https://velog.io/@moonyoung/JPA-QueryDSL-Fetch-FetchJoin)