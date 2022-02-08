# Koroad_Quiz
## Data
- [학과시험 문제은행.pdf](https://www.safedriving.or.kr/notice/rerBankView.do;jsessionid=8SdYASFc8Xq4X0sDLXs1l4iYDCaXlavbl8MMRz496nDqFo4bpp1FN6f35faiBDLB.apdis02_servlet_MWEB?menuCode=MN-PO-1151)
- Python를 활용한 데이터 전처리
  - [PyMuPDF] 문제 이미지 분리
  - [pdftotext, re] 문제 텍스트 전처리

## 어플 소개
<a href='https://play.google.com/store/apps/details?id=com.Koroad.koroad_quiz&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='다운로드하기 Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/ko_badge_web_generic.png' width="150"/></a>
- 보기가 4개인 문제와 보기가 5개인 문제가 혼합되어 있으므로 상황에 따라 보기5를 논클릭 처리
- 해설 클릭시 문제 해설을 팝업 형식으로 출력
- 특정 보기를 선택하면 다음 문제로 넘어갈 수 있는 버튼이 활성화되며, 보기의 배경을 초록색과 빨강색으로 바꾸는 방식으로 해당 보기의 정답여부 판별
  - Version 3.0 에서 변경
    - 정답이 여러개인 문제를 체크하기 위해 보기를 선택시마다 보기의 selected 상태가 변경
    - 별도의 버튼을 통해 정답여부 메세지 출력
- 이미지, 영상이 포함된 문제의 경우 해당 레이아웃의 높이를 조정하여 활성화


### App Layout
|시작화면|문제화면|결과화면|
|:---:|:---:|:---:|
<img src="https://user-images.githubusercontent.com/50973778/150240018-5dbad161-87c9-491b-983f-943661ad3e50.jpg" width="150" height="300"/>|<img src="https://user-images.githubusercontent.com/50973778/152924662-9029b994-81b4-4d3b-b2d7-9fb666b661ad.jpg" width="150" height="300"/>|<img src="https://user-images.githubusercontent.com/50973778/152924714-e8944368-44d9-4b02-8f1e-b5356c34ae0c.jpg" width="150" height="300"/>
|해설 팝업|정답 팝업|오답 팝업|
<img src="https://user-images.githubusercontent.com/50973778/152924937-04c90397-f1cb-4af7-b1d8-953e86116467.jpg" width="150" height="300"/>|<img src="https://user-images.githubusercontent.com/50973778/152925011-9fbe8880-c627-4aa6-ba54-fb3dd5aa1abb.jpg" width="150" height="300"/>|<img src="https://user-images.githubusercontent.com/50973778/152925077-79d32d2c-8bba-4bd2-b2c4-cfc111293e44.jpg" width="150" height="300"/>

### App 기능
|화면|특징|
|:---:|:---|
|<img src="https://user-images.githubusercontent.com/50973778/152926939-155ac43f-4aaa-45bb-890f-0f2519ea7ee0.gif" width="150" height="300"/>|- 보기 체크시 다음 문제 버튼 활성화</br>- 사용자 편의성을 높이기 위해 다른 보기 선택시 이전 선택보기 상태 변경</br>- 정답체크 기능, 힌트 보기 가능</br>- 문제 아래 부분의 진행바를 통해 현재 진행도 확인 가능|
|<img src="https://user-images.githubusercontent.com/50973778/152927501-f0b55295-e5ff-40f6-bf97-5b1f80cc6637.gif" width="150" height="300"/>|- 보기 2개 체크시 다음 문제 버튼 활성화</br>- 정답체크 기능, 힌트 보기 가능</br>- 문제 아래 부분의 진행바를 통해 현재 진행도 확인 가능|


## Reference
- https://github.com/MyStoryG/CapitalQuiz
- https://github.com/Solideizer/QuizApp
- https://github.com/Spikeysanju/MotionToast
- https://github.com/loukwn/StageStepBar
