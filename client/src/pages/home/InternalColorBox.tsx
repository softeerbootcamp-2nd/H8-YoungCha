import { TrimType } from '@/types';
import OptionLayout from './OptionLayout';
import { TITLE } from './constant';

interface InternalColorBoxProps {
  trims: TrimType[];
}

function InternalColorBox({ trims }: InternalColorBoxProps) {
  return (
    <OptionLayout title={TITLE.INTERNAL_COLOR}>
      {trims.map(({ interiorColors }, index) => (
        <ul
          className="flex flex-col w-full gap-24px"
          key={`internal-color-${index}`}
        >
          {interiorColors.map(({ imgUrl, name }, index) => (
            <li
              className="flex items-center gap-12px"
              key={`internal-color-${name}-${index}`}
            >
              <img
                src={imgUrl}
                alt={name}
                className="w-100px h-35px rounded-8px"
              />
              <p className="font-medium body2 text-grey-black">{name}</p>
            </li>
          ))}
        </ul>
      ))}
    </OptionLayout>
  );
}

export default InternalColorBox;
