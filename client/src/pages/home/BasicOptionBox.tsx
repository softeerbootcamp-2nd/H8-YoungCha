import OptionLayout from './OptionLayout';

interface DataType {
  name: string;
  categoryId: number;
  imgUrl: string;
}

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
    <div className="flex flex-col gap-16px max-w-7xl">
      <h3 className="font-medium text-center text-grey-black py-8px">
        {TITLE}
      </h3>
      <div className="flex flex-col gap-32px">
        <div className="flex justify-center gap-8px">
          {BasicOptions.map((option) => {
            return (
              <div
                className="flex items-center justify-center h bg-grey-001 text-grey-003 px-22px py-6px rounded-6px"
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
              className="flex flex-col w-full gap-24px"
              key={data[0].categoryId + index}
            >
              {data.map(({ name, categoryId, imgUrl }) => (
                <li className="flex items-center gap-15px" key={categoryId}>
                  <img
                    src={imgUrl}
                    alt={name}
                    className="w-80px h-60px rounded-6px"
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
