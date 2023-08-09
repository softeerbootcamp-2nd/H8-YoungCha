import * as Icon from '../../assets/icons';
import OptionLayout from './OptionLayout';

type DataType = { code: string; description: string };
interface OutsideColorBoxProps {
  datas: DataType[][];
}

const TITLE = '외장 색상';

function OutsideColorBox({ datas }: OutsideColorBoxProps) {
  return (
    <OptionLayout title={TITLE}>
      {datas.map((data) => (
        <ul className="flex flex-col w-full gap-24px" key={data[0].description}>
          {data.map(({ code, description }) => (
            <li className="flex items-center gap-15px" key={description}>
              <Icon.MainOutsideColor code={code} />
              <p className="body2 text-grey-004">{description}</p>
            </li>
          ))}
        </ul>
      ))}
    </OptionLayout>
  );
}

export default OutsideColorBox;
