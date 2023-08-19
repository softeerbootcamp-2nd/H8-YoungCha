import { TrimType } from '@/types';
import OptionLayout from './OptionLayout';
import { TITLE } from './constant';

interface ExteriorColorBoxProps {
  trims: TrimType[];
}

function ExteriorColorBox({ trims }: ExteriorColorBoxProps) {
  return (
    <OptionLayout title={TITLE.EXTERIR_COLOR}>
      {trims.map(({ exteriorColors }, index) => (
        <ul
          className="flex flex-col w-full gap-24px"
          key={`exterior-color-${index}`}
        >
          {exteriorColors.map(({ imgUrl, name }) => (
            <li className="flex items-center gap-15px" key={name}>
              <img
                src={imgUrl}
                alt={name}
                className="rounded-full w-20px h-20px"
              />
              <p className="body2 text-grey-004">{name}</p>
            </li>
          ))}
        </ul>
      ))}
    </OptionLayout>
  );
}

export default ExteriorColorBox;
