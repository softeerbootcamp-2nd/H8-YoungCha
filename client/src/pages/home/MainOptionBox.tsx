import { TrimType } from '@/assets/mock/mock';
import OptionLayout from './OptionLayout';

interface MainOptionBoxProps {
  trims: TrimType[];
}

const TITLE = '핵심 옵션';

function MainOptionBox({ trims }: MainOptionBoxProps) {
  return (
    <OptionLayout title={TITLE}>
      {trims.map(({ mainOptions }, index) => (
        <ul
          className="flex flex-col w-full gap-8px"
          key={`main-option-${index}`}
        >
          {mainOptions.map(({ imgUrl, description }, index) => (
            <li
              className="flex items-center gap-8px"
              key={`main-option-imgae-${index}`}
            >
              <img src={imgUrl} alt="description" className="w-80px h-60px" />
              <p className="body3 text-grey-004">{description}</p>
            </li>
          ))}
        </ul>
      ))}
    </OptionLayout>
  );
}

export default MainOptionBox;
