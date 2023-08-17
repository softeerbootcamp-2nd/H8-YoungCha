import ActiveCheck from '@/assets/icons/select-check';
import InactiveCheck from '@/assets/icons/select-check-inactive';
import SelectButton from '@/components/SelectButton';
import { useState } from 'react';
import { Link } from 'react-router-dom';

type GenderType = '남성' | '여성' | '선택 안함';
const genderSelectArray: Array<GenderType> = ['남성', '여성', '선택 안함'];

function GenderSelectButtonsBox() {
  const [hoveredGender, setHoveredGender] = useState<GenderType | ''>('');

  return (
    <div className={`flex flex-col w-343px gap-12px mt-173px`}>
      {genderSelectArray.map((Gender: GenderType) => {
        const isHovered = hoveredGender === Gender;

        return (
          <Link to="/model/LX06/guide/keyword" key={Gender}>
            <SelectButton
              type={isHovered ? 'iconActive' : 'default'}
              size={'lg'}
              onMouseEnter={() => setHoveredGender(Gender)}
              onMouseLeave={() => setHoveredGender('')}
            >
              {Gender}
              {isHovered ? <ActiveCheck /> : <InactiveCheck />}
            </SelectButton>
          </Link>
        );
      })}
    </div>
  );
}

export default GenderSelectButtonsBox;
