import { PowerTrain } from '@/assets/mock/optionMock';
import { useOptionCardState } from '@/store/useOptionCardContext';

interface FunctionDetailBoxProps extends Pick<PowerTrain, 'details'> {}

function FunctionDetailBox({ details }: FunctionDetailBoxProps) {
  const { isActive } = useOptionCardState();
  return (
    <div
      className={`${
        isActive ? 'bg-[#f3f3f3]' : 'bg-grey-001'
      }  rounded-6px py-12px mt-8px`}
    >
      {details[0].specs.map((item, index) => (
        <div
          key={`FunctionDetail-${index}`}
          className={`${isActive ? 'text-[#4B4B4B]' : 'text-grey-003'} body3`}
        >
          <span className="p-14px">{item.name}</span>
          <span>{item.description}</span>
        </div>
      ))}
    </div>
  );
}

export default FunctionDetailBox;