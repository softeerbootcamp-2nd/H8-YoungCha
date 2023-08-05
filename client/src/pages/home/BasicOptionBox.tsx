const BasicOptions = [
  '전체',
  '성능',
  '지능형 안전기술',
  '안전',
  '외관',
  '내장',
  '시트',
  '편의',
  '멀티미디어',
];

function BasicOptionBox() {
  return (
    <>
      <h3 className="font-medium text-grey-black mb-[28px] mt-[56px] text-center">
        기본 포함 품목
      </h3>
      <div className="flex justify-center gap-[8px] mb-[32px]">
        {BasicOptions.map((option) => {
          return (
            <div
              className="h-[32px] bg-grey-001 text-grey-003 flex justify-center items-center px-[22px] py-[6px] rounded-[6px]"
              key={option}
            >
              {option}
            </div>
          );
        })}
      </div>
      <div className="flex justify-between mb-[52px]">
        <ul className="flex flex-col gap-[41px]">
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
        </ul>
        <ul className="flex flex-col gap-[41px]">
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
        </ul>
        <ul className="flex flex-col gap-[41px]">
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
        </ul>
        <ul className="flex flex-col gap-[41px]">
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
          <li className="flex gap-[16px] items-center">
            <img
              src="src/assets/images/8-auto-changer.png"
              alt="8단 자동변속기"
              className="w-[80px] h-[60px] rounded-[6px]"
            />
            <p className="font-medium body2">8단 자동 변속기</p>
          </li>
        </ul>
      </div>

      <div className="flex justify-center mb-[100px]">
        <div className="w-[140px] h-[50px] bg-grey-001 rounded-[6px] flex justify-center items-center">
          더보기
        </div>
      </div>
    </>
  );
}

export default BasicOptionBox;
