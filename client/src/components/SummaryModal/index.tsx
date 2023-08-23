import { Fragment, HTMLAttributes } from 'react';
import { OptionGroupType } from '@/pages/making/type';
import { formatPrice } from '@/utils';
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
    <div className="relative h-0 select-none">
      <Transition
        className="absolute bottom-0 z-10 left-16px right-16px transform-gpu max-h-[calc(100vh-256px)] bg-white rounded-t-6px border-x-2px border-t-2px border-grey-001 drop-shadow-lg"
        render={render}
        from="translate-y-full opacity-0"
        to="translate-y-0 opacity-100"
        unmount
      >
        <div className="flex items-center justify-between px-20px py-16px">
          <h3 className="font-medium text-main-blue title3">
            {ESTIMATION_SUMMARY}
          </h3>
          <button onClick={onClose}>
            <Close className="fill-grey-003" />
          </button>
        </div>

        <div className="max-h-[calc(100vh-312px)] overflow-y-auto">
          <div className="flex flex-col h-full">
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
      </Transition>
    </div>
  );
}

function OptionTitle({ title, price }: OptionTitleProps) {
  return (
    <div className="flex items-center justify-between font-medium text-main-blue bg-grey-001 px-20px pt-10px pb-8px title4">
      <div>{title}</div>
      <div>{formatPrice(price, true)}</div>
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
        <div className="text-right">{formatPrice(price, true)}</div>
      </div>
    </>
  );
}

export default SummaryModal;