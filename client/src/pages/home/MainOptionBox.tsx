import { TrimType } from '@/assets/mock/mock';
import OptionLayout from './OptionLayout';
import { TITLE } from './constant';
import removeBracket from '../../utils/removeBracket';

interface MainOptionBoxProps {
  trims: TrimType[];
}

function MainOptionBox({ trims }: MainOptionBoxProps) {
  return (
    <OptionLayout title={TITLE.MAIN_OPTION}>
      {trims.map(({ mainOptions }, index) => (
        <ul
          className="flex flex-col w-full gap-8px"
          key={`main-option-${index}`}
        >
          {mainOptions.map(({ imgUrl, name }, index) => (
            <li
              className="flex items-center gap-8px"
              key={`main-option-imgae-${index}`}
            >
              <img src={imgUrl} alt="name" className="w-80px h-60px" />
              <p className="body3 text-grey-004">{removeBracket(name)}</p>
            </li>
          ))}
        </ul>
      ))}
    </OptionLayout>
  );
}

export default MainOptionBox;
