import { TrimType } from '@/assets/mock/mock';
import * as Icon from '../../assets/icons';
import OptionLayout from './OptionLayout';

interface ExteriorColorBoxProps {
  trims: TrimType[];
}

const TITLE = '외장 색상';

function ExteriorColorBox({ trims }: ExteriorColorBoxProps) {
  return (
    <OptionLayout title={TITLE}>
      {trims.map(({ exteriorColor }, index) => (
        <ul
          className="flex flex-col w-full gap-24px"
          key={`exterior-color-${index}`}
        >
          {exteriorColor.map(({ code, name }) => (
            <li className="flex items-center gap-15px" key={name}>
              <Icon.MainOutsideColor code={code} />
              <p className="body2 text-grey-004">{name}</p>
            </li>
          ))}
        </ul>
      ))}
    </OptionLayout>
  );
}

export default ExteriorColorBox;
