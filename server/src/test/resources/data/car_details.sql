INSERT INTO car (id, name)
VALUES (1, '팰리세이드');

INSERT INTO trim (id, name, img_url, background_img_url, hashtag, price, description, car_id)
VALUES (2, 'Le Blanc (르블랑)', 'leblancImgUrl', 'leblancBgImgUrl', '베스트셀러', 40440000, '모두가 선택한 베스트셀러', 1),
       (5, 'Guide Mode', 'guideModeImgUrl', 'guideModeBgImgUrl', '나만을 위한 팰리세이드', 38960000, null, 1);

INSERT INTO category (id, name)
VALUES (1, '파워 트레인'),
       (2, '구동 방식'),
       (3, '바디 타입'),
       (4, '외장 색상'),
       (5, '내장 색상'),
       (6, '휠'),
       (7, '시스템'),
       (8, '온도 관리'),
       (9, '외부 장치'),
       (10, '내부 장치'),
       (11, '성능'),
       (12, '지능형 안전기술'),
       (13, '안전'),
       (14, '외관'),
       (15, '내관'),
       (16, '시트'),
       (17, '편의'),
       (18, '멀티미디어');

INSERT INTO options (id, name, price, feedback, category_id)
VALUES (1, '디젤 2.2', 1480000, '디젤 엔진은 효율이 좋아요! 효율을 중시한다면, 탁월한 선택입니다.', 1),
       (2, '가솔린 3.8', 0, '가솔린 엔진은 조용해요! 조용한 주행을 원하신다면, 탁월한 선택입니다.', 1),
       (3, '2WD', 0, '2WD는 효율이 좋아요! 효율을 중시한다면, 탁월한 선택입니다.', 2),
       (4, '4WD', 2370000, '4WD는 파워풀해요! 힘있는 주행을 원하신다면, 탁월한 선택입니다.', 2),
       (5, '7인승', 0, '통로가 있어 쾌적해요! 쾌적함을 원하신다면, 탁월한 선택입니다.', 3),
       (6, '8인승', 0, '공간 활용을 원하신다면, 탁월한 선택입니다.', 3),
       (7, '어비스 블랙펄', 0, '어비스 블랙펄은 깊은 검정색의 외장색상으로, 차량에 고급스러움과 우아함을 더해줍니다.', 4),
       (8, '쉬머링 실버 메탈릭', 0, '쉬머링 실버 메탈릭은 은색 계열의 외장색상으로, 차량에 현대적이고 세련된 분위기를 더해줍니다.', 4),
       (9, '문라이트 블루 펄', 0, '문라이트 블루 펄은 밝은 파란색의 외장색상으로, 차량에 상쾌하고 활기찬 느낌을 줍니다.', 4),
       (10, '가이아 브라운 펄', 0, '가이아 브라운 펄은 브라운 계열의 외장색상으로, 차량에 고급스러움과 차분한 분위기를 부여합니다.', 4),
       (11, '그라파이트 그레이 메탈릭', 0, '그라파이트 그레이 메탈릭은 회색 계열의 외장색상으로, 차량에 현대적이고 세련된 분위기를 부여합니다.', 4),
       (12, '크리미 화이트 펄', 100000, '크리미 화이트 펄은 밝은 화이트(흰색)의 외장색상으로, 차량에 깨끗하고 우아한 느낌을 줍니다.', 4),
       (13, '퀄팅천연(블랙)', 0, '블랙 컬러는 클래식한 분위기에요! 클래식한 분위기를 원하신다면, 탁월한 선택입니다.', 5),
       (14, '쿨그레이', 0, '쿨그레이는 환한 분위기에요! 환한 분위기를 원하신다면, 탁월한 선택입니다.', 5),
       (15, '20인치 알로이 휠', 0, '20인치 알로이 휠은 충격흡수가 잘돼요! 뛰어난 승차감을 원하신다면, 탁월한 선택입니다.', 6),
       (16, '20인치 다크 스퍼터링 휠', 840000, '20인치 다크 스퍼터링 휠은 깔끔한 디자인이에요! 세련되고 깔끔한 디자인을 원하신다면, 탁월한 선택입니다.', 6),
       (17, '20인치 블랙톤 전면 가공 휠', 840000, '20인치 블랙톤 전면 가공 휠은 무게감이 있어요! 무게감있는 디자인을 원하신다면, 탁월한 선택입니다.', 6),
       (18, '알콘(alcon) 단조 브레이크 & 20인치 휠 패키지', 3660000,
        '알콘(alcon) 단조 브레이크 & 20인치 휠 패키지는 뛰어난 제동력이 강점이에요! 안전과 디자인을 모두 원하신다면, 탁월한 선택입니다.', 6),
       (19, '현대스마트센스 1', 790000, 'null', 7),
       (20, '주차보조 시스템 2', 690000, 'null', 7),
       (21, '컴포트 2', 1090000, 'null', 7),
       (22, '2열 통풍시트', 400000, 'null', 8),
       (23, '적외선 무릎워머', 300000, 'null', 8),
       (24, '차량 보호 필름', 490000, 'null', 9),
       (25, '사이드 스텝', 350000, 'null', 9),
       (26, '듀얼 머플러 패키지', 840000, 'null', 9),
       (27, '듀얼 와이드 선루프', 890000, 'null', 10),
       (28, '프로텍션 매트 패키지 1', 250000, 'null', 10),
       (29, '빌트인 공기청정기', 400000, 'null', 10),
       (30, '빌트인 캠(보조배터리 포함)', 690000, 'null', 10),
       (31, '8단 자동변속기', 0, 'null', 1),
       (32, 'ISG 시스템', 0, 'null', 1),
       (33, '통합주행모드', 0, 'null', 1),
       (34, '랙구동형 전동식 파워스티어링(R-MDPS)', 0, 'null', 1),
       (35, '전자식 변속버튼', 0, 'null', 1),
       (36, '하이빔 보조', 0, 'null', 12),
       (37, '진동 경고 스티어링 휠', 0, 'null', 12),
       (38, '스마트 크루즈 컨트롤(스탑앤고 기능 포함)', 0, 'null', 12),
       (39, '내비게이션 기반 스마트 크루즈 컨트롤 (안전구간, 곡선로)', 0, 'null', 12),
       (71, '베젤리스 인사이드 미러', 0, 'null', 15),
       (107, '12.3인치 내비게이션(블루링크, 폰 프로젝션, 현대 카페이)', 0, 'null', 18);

INSERT INTO trim_options (id, type, trim_id, options_id)
VALUES (85, 1, 2, 1),
       (86, 1, 2, 2),
       (87, 1, 2, 3),
       (88, 1, 2, 4),
       (89, 1, 2, 5),
       (90, 1, 2, 6),
       (91, 1, 2, 7),
       (92, 1, 2, 8),
       (93, 1, 2, 9),
       (94, 1, 2, 10),
       (95, 1, 2, 11),
       (96, 1, 2, 12),
       (97, 1, 2, 13),
       (98, 1, 2, 14),
       (99, 1, 2, 15),
       (100, 1, 2, 16),
       (101, 1, 2, 17),
       (102, 1, 2, 18),
       (103, 1, 2, 19),
       (104, 1, 2, 20),
       (105, 1, 2, 21),
       (106, 1, 2, 22),
       (107, 1, 2, 23),
       (108, 1, 2, 24),
       (109, 1, 2, 25),
       (110, 1, 2, 26),
       (111, 1, 2, 27),
       (112, 1, 2, 28),
       (113, 1, 2, 29),
       (114, 1, 2, 30),
       (1, 0, 2, 31),
       (2, 0, 2, 32),
       (3, 0, 2, 33),
       (4, 0, 2, 34),
       (5, 0, 2, 35),
       (6, 0, 2, 36),
       (7, 0, 2, 37),
       (8, 0, 2, 38),
       (9, 2, 2, 39),
       (40, 2, 2, 71),
       (76, 2, 2, 107);

INSERT INTO options_image (id, img_url, img_type, options_id)
VALUES (1, 'dieselengine2.2.jpg', 0, 1),
       (2, 'gasoline3.8.jpg', 0, 2),
       (3, 'lx_2wd_s.jpg', 0, 3),
       (4, 'lx_htrac_s.jpg', 0, 4),
       (5, 'lx_htrac_s.jpg', 0, 5),
       (6, 'lx_8seats_s.jpg', 0, 6),
       (7, 'img-interior.png', 0, 13),
       (8, 'qualting-black.png', 1, 13),
       (9, 'img-interior.png', 0, 14),
       (10, 'cool-gray', 1, 14),
       (11, '20alloywheel.jpg', 0, 15),
       (12, 'logo-npp.png', 2, 15),
       (13, '20_darkwheel.jpg', 0, 16),
       (14, 'logo-npp.png', 2, 16),
       (15, '20inchwheel.jpg', 0, 17),
       (16, 'logo-npp.png', 2, 17),
       (17, '20inchwheel.jpg', 0, 18),
       (18, 'logo-npp.png', 2, 18),
       (19, 'fca2.jpg', 0, 19),
       (20, 'pca.jpg', 0, 20),
       (21, 'roa.jpg', 0, 21),
       (22, '2_cooling.jpg', 0, 22),
       (23, 'kneewarmer.jpg', 0, 23),
       (24, 'logo-hga.png', 2, 23),
       (25, 'carprotector.jpg', 0, 24),
       (26, 'logo-hga.png', 2, 24),
       (27, 'sidestep.jpg', 0, 25),
       (28, 'logo-hga.png', 2, 25),
       (29, 'dualmufflerpackage.jpg', 0, 26),
       (30, 'logo-hga.png', 2, 26),
       (31, 'dualwidesunroof.jpg', 0, 27),
       (32, 'luggageprotectmat.jpg', 0, 28),
       (33, 'logo-hga.png', 2, 28),
       (34, 'builtinaircleaner.jpg', 0, 29),
       (35, 'logo-hga.png', 2, 29),
       (36, 'builtincam.jpg', 0, 30),
       (37, 'logo-hga.png', 2, 30),
       (38, '8at_s.jpg', 1, 31),
       (39, 'isg_s.jpg', 1, 32),
       (40, 'drivemode_s.jpg', 1, 33),
       (41, 'rmdps_s.jpg', 1, 34),
       (42, 'sbw_s.jpg', 1, 35),
       (43, 'hba_s.jpg', 1, 36),
       (44, 'vibsteeringwheel_s.jpg', 1, 37),
       (45, 'scc_s.jpg', 1, 38),
       (46, 'cruise-control.jpg', 1, 39),
       (47, 'black-pearl.png', 1, 7),
       (48, 'silver-metalic.png', 1, 8),
       (49, 'blue-pearl.png', 1, 9),
       (50, 'brown-pearl.png', 1, 10),
       (127, 'gray-metalic.png', 1, 11),
       (128, 'white-pearl.png', 1, 12),
       (77, 'inside-mirror.jpg', 1, 71),
       (113, '12-3-navigation.png', 1, 107);