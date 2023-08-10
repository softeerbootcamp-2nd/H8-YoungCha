import { TrimType } from '@/assets/mock/mock';
import OptionLayout from './OptionLayout';

interface InternalColorBoxProps {
  trims: TrimType[];
}

const TITLE = '내장 색상';

function InternalColorBox({ trims }: InternalColorBoxProps) {
  return (
    <OptionLayout title={TITLE}>
      {trims.map(({ internalColor }, index) => (
        <ul
          className="flex flex-col w-full gap-24px"
          key={`internal-color-${index}`}
        >
          {internalColor.map(({ url, name }, index) => (
            <li
              className="flex items-center gap-12px"
              key={`internal-color-${name}-${index}`}
            >
              <img src={url} alt={name} className="w-100px h-35px" />
              <p className="body2 text-grey-black">{name}</p>
            </li>
          ))}
        </ul>
      ))}
    </OptionLayout>
  );
}

export default InternalColorBox;
