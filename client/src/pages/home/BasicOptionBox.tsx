import OptionLayout from './OptionLayout';

type DataType = {
  name: string;
  categoryId: number;
  imgUrl: string;
};

interface BasicOptionBoxProps {
  datas: DataType[][];
}

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

const TITLE = '기본 포함 품목';

function BasicOptionBox({ datas }: BasicOptionBoxProps) {
  return (
    <div className="flex flex-col gap-[16px] max-w-7xl">
      <h3 className="font-medium text-center text-grey-black py-[8px]">
        {TITLE}
      </h3>
      <div className="flex flex-col gap-[32px]">
        <div className="flex justify-center gap-[8px]">
          {BasicOptions.map((option) => {
            return (
              <div
                className="h bg-grey-001 text-grey-003 flex justify-center items-center px-[22px] py-[6px] rounded-[6px]"
                key={option}
              >
                {option}
              </div>
            );
          })}
        </div>
        <OptionLayout>
          {datas.map((data, index) => (
            <ul
              className="flex flex-col gap-[24px] w-full"
              key={data[0].categoryId + index}
            >
              {data.map(({ name, categoryId, imgUrl }) => (
                <li className="flex gap-[15px] items-center" key={categoryId}>
                  <img
                    src={imgUrl}
                    alt={name}
                    className="w-[80px] h-[60px] rounded-[6px]"
                  />
                  <p className="font-medium body2">{name}</p>
                </li>
              ))}
            </ul>
          ))}
        </OptionLayout>
      </div>
    </div>
  );
}

export default BasicOptionBox;

{
  /* <>
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
</>; */
}
