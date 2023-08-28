import { Fragment, useState } from 'react';
import SubOptionDescription from '@/components/OptionCard/AdditionalContents/SubOptionDescription.tsx';
import { OptionDetailType } from '@/types/option.ts';
interface SubOptionsProps {
  options: OptionDetailType[];
  isActive: boolean;
}

function SubOptions({ options, isActive }: SubOptionsProps) {
  const [subSelectedIndex, setsubSelectedIndex] = useState(0);

  function handleOnClick(
    event: React.MouseEvent<HTMLParagraphElement>,
    index: number
  ) {
    isActive && event.stopPropagation();
    setsubSelectedIndex(index);
  }

  return (
    <div className="flex flex-wrap text-grey-003 body3 mb-8px">
      {options.map((item, index) => (
        <Fragment key={`SubOption-${index}`}>
          <p
            className={`${
              isActive && subSelectedIndex === index ? 'text-grey-black' : ''
            } ${isActive ? 'font-medium' : ''}`}
            onClick={(event) => handleOnClick(event, index)}
            role="none"
          >
            {item.name}
          </p>
          {index !== options.length - 1 && <p>ㆍ</p>}
        </Fragment>
      ))}
      <SubOptionDescription isActive={isActive}>
        {options[subSelectedIndex].description}
      </SubOptionDescription>
    </div>
  );
}

export default SubOptions;
