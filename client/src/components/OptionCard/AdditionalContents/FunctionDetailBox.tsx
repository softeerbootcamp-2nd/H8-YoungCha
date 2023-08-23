import { AllOptionType } from '@/types/option';

interface FunctionDetailBoxProps extends Pick<AllOptionType, 'details'> {
  isActive: boolean;
}

function FunctionDetailBox({ details, isActive }: FunctionDetailBoxProps) {
  if (details[0]?.specs) return null;
  return (
    <div className={`bg-grey-001 rounded-6px p-12px flex flex-col gap-8px`}>
      {details[0]?.specs?.map((item, index) => (
        <div
          key={`FunctionDetail-${index}`}
          className={`${
            isActive ? 'text-[#4B4B4B]' : 'text-grey-003'
          } body3 grid grid-cols-5 gap-x-12px`}
        >
          <span>{item.name}</span>
          <span className="col-span-4">{item.description}</span>
        </div>
      ))}
    </div>
  );
}

export default FunctionDetailBox;
