import OptionLayout from './OptionLayout';

interface DataType {
  url: string;
  description: string;
}
interface OutsideColorBoxProps {
  insideColorLists: DataType[][];
}

const TITLE = '내장 색상';

function InsideColorBox({ insideColorLists }: OutsideColorBoxProps) {
  return (
    <OptionLayout title={TITLE}>
      {insideColorLists.map((insideColor) => (
        <ul
          className="flex flex-col w-full gap-24px"
          key={insideColor[0].description}
        >
          {insideColor.map(({ url, description }) => (
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
