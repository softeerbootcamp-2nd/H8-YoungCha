import OptionLayout from './OptionLayout';

type DataType = { url: string; description: string };
interface OutsideColorBoxProps {
  datas: DataType[][];
}

const TITLE = '내장 색상';

function InsideColorBox({ datas }: OutsideColorBoxProps) {
  return (
    <OptionLayout title={TITLE}>
      {datas.map((data) => (
        <ul
          className="flex flex-col gap-[24px] w-full"
          key={data[0].description}
        >
          {data.map(({ url, description }) => (
            <li className="flex gap-[12px] items-center" key={description}>
              <img src={url} alt={description} className="w-[100px] h-[35px]" />
              <p className="body2 text-grey-black">{description}</p>
            </li>
          ))}
        </ul>
      ))}
    </OptionLayout>
  );
}

export default InsideColorBox;
