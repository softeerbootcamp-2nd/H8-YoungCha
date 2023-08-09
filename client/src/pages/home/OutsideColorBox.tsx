import * as Icon from '../../assets/icons';
import OptionLayout from './OptionLayout';

interface DataType {
  code: string;
  description: string;
}
interface OutsideColorBoxProps {
  outsideColorLists: DataType[][];
}

const TITLE = '외장 색상';

function OutsideColorBox({ outsideColorLists }: OutsideColorBoxProps) {
  return (
    <OptionLayout title={TITLE}>
      {outsideColorLists.map((outsideColor) => (
        <ul
          className="flex flex-col w-full gap-24px"
          key={outsideColor[0].description}
        >
          {outsideColor.map(({ code, description }) => (
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
