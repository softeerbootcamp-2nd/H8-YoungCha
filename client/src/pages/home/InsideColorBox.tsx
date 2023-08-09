import OptionLayout from './OptionLayout';

interface DataType {
  url: string;
  description: string;
}
interface OutsideColorBoxProps {
  datas: DataType[][];
}

const TITLE = '내장 색상';

function InsideColorBox({ datas }: OutsideColorBoxProps) {
  return (
    <OptionLayout title={TITLE}>
      {datas.map((data) => (
        <ul className="flex flex-col w-full gap-24px" key={data[0].description}>
          {data.map(({ url, description }) => (
            <li className="flex items-center gap-12px" key={description}>
              <img src={url} alt={description} className="w-100px h-35px" />
              <p className="body2 text-grey-black">{description}</p>
            </li>
          ))}
        </ul>
      ))}
    </OptionLayout>
  );
}

export default InsideColorBox;
