import { Fragment, HTMLAttributes } from 'react';
import { OptionGroupType } from '@/pages/making/type';
import { getPriceTemplete } from '@/utils';
import Transition from '../Transition/Transition';
import { Close } from '@/assets/icons';
import { mockUserSelectedOptionData } from '@/assets/mock/mock';

const ESTIMATION_SUMMARY = '견적 요약';

interface SummaryModalProps {
  render: boolean;
  onClose: () => void;
}

interface OptionTitleProps extends HTMLAttributes<HTMLDivElement> {
  title: string;
  price: number;
}

interface OptionProps extends HTMLAttributes<HTMLDivElement> {
  type: string;
  name: string;
  price: number;
}

function SummaryModal({ render, onClose }: SummaryModalProps) {
  return (
    <Transition
      className="absolute bottom-0 z-10 flex items-center justify-center transform"
      render={render}
      from="translate-y-full opacity-0"
      to="translate-y-0 opacity-100"
      unmount
    >
      <div className="bg-white w-375px rounded-t-6px border-2px border-grey-001 drop-shadow-lg max-h-560px">
        <div className="flex justify-end px-16px pt-16px">
          <button onClick={onClose}>
            <Close className="fill-grey-003" />
          </button>
        </div>
        <div className="flex flex-col gap-y-16px">
          <div className="flex items-center justify-between text-main-blue px-20px py-10px">
            <h1 className="title3">{ESTIMATION_SUMMARY}</h1>
          </div>
          <div className="overflow-y-scroll max-h-360px mb-90px">
            {Object.values(mockUserSelectedOptionData).map((optionGroup) => {
              const { title, options } = optionGroup as OptionGroupType;
              const totalPrice = Object.values(options).reduce(
                (sum, { price }) => sum + price!,
                0
              );

              return (
                <Fragment key={title}>
                  <OptionTitle title={title} price={totalPrice} />
                  <div className="flex flex-col p-20px gap-y-10px">
                    {Object.values(options).map(
                      ({ type, name, price }, index) => {
                        const isDuplicatedType = title === '옵션' && index > 0;
                        return (
                          <Option
                            type={isDuplicatedType ? '' : type ?? ''}
                            name={name}
                            price={price!}
                            key={name}
                          />
                        );
                      }
                    )}
                  </div>
                </Fragment>
              );
            })}
          </div>
        </div>
      </div>
    </Transition>
  );
}

function OptionTitle({ title, price }: OptionTitleProps) {
  return (
    <div className="flex items-center justify-between text-main-blue bg-grey-001 px-20px pt-10px pb-8px title4">
      <div>{title}</div>
      <div>{getPriceTemplete(price, true)}</div>
    </div>
  );
}
interface OptionProps extends HTMLAttributes<HTMLDivElement> {
  type: string;
  name: string;
  price: number;
}

function Option({ type, name, price }: OptionProps) {
  return (
    <>
      <div className="grid grid-cols-4 body2 text-grey-black">
        <div className="text-grey-003">{type}</div>
        <div className="col-span-2">{name}</div>
        <div className="text-right">{getPriceTemplete(price, true)}</div>
      </div>
    </>
  );
}

export default SummaryModal;
