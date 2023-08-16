import ActiveCheck from '@/assets/icons/select-check';
import InactiveCheck from '@/assets/icons/select-check-inactive';
import SelectButton from '@/components/SelectButton';
import { useState } from 'react';
import { Link } from 'react-router-dom';

type AgeType = '20대' | '30대' | '40대' | '50대' | '60대' | '70대';

const ageSelectArray: Array<AgeType> = [
  '20대',
  '30대',
  '40대',
  '50대',
  '60대',
  '70대',
];

function AgeSelectButtonsBox() {
  const [hoveredAge, setHoveredAge] = useState<AgeType | ''>('');

  return (
    <div className={`flex flex-col w-343px gap-12px justify-center mt-98px`}>
      {ageSelectArray.map((age: AgeType) => {
        const isHovered = hoveredAge === age;

        return (
          <Link to="/model/LX06/guide/gender" key={age}>
            <SelectButton
              type={isHovered ? 'iconActive' : 'default'}
              size={'lg'}
              onMouseEnter={() => setHoveredAge(age)}
              onMouseLeave={() => setHoveredAge('')}
            >
              {age}
              {isHovered ? <ActiveCheck /> : <InactiveCheck />}
            </SelectButton>
          </Link>
        );
      })}
    </div>
  );
}

export default AgeSelectButtonsBox;
