import { useContext, useState } from 'react';
import Button from '@/components/Button';
import SummaryOptionsBox from './SummaryOptionsBox';
import { CAR_COLOR, COMPLETE_OPTION_PAGE_TITLE } from '../constant';
import { ColorType, OptionGroupType } from '../type';
import DetailOptionBox from '../detail-option-box/DetailOptionBox';
import DetailSelectOptionBox from '../detail-option-box/DetailSelectOptionBox';
import DetailBasicOptionBox from '../detail-option-box/DetailBasicOptionBox';
import Confetti from '@/components/Confetti';
import { UserSelectedOptionDataContext } from '..';

function CompleteOptionPage() {
  const [selectedColorType, setSelectedColorType] =
    useState<ColorType>('exterior');
  const activeColor = 'text-white bg-main-blue';
  const inactiveColor = 'text-main-blue bg-grey-001';

  const { userSelectedOptionData } = useContext(UserSelectedOptionDataContext);

  return (
    <div>
      <Confetti particleCount={120} circleParticleCount={60} />
      <div className="flex flex-col items-center w-full m-auto mt-60px gap-60px max-w-7xl px-128px pb-70px">
        <div className="flex flex-col items-center w-full">
          <h1 className="whitespace-pre-line text-34px font-medium leading-[47.6px] tracking-[-1.36px] font-hsans-head text-grey-black text-center">
            {COMPLETE_OPTION_PAGE_TITLE}
          </h1>
          <img
            src="/src/assets/mock/images/palisade.png"
            alt="palisade"
            width={400}
            height={200}
          />
          <div className="flex">
            {CAR_COLOR.map(({ text, type }) => (
              <button
                className={`${
                  selectedColorType === type ? activeColor : inactiveColor
                } py-8px px-32px title4`}
                key={type}
                onClick={() => setSelectedColorType(type)}
              >
                {text}
              </button>
            ))}
          </div>
        </div>

        <div className="w-full">
          <div className="flex items-center justify-between h-68px px-24px">
            <span className="title3 text-grey-black">견적 요약</span>
            <div className="flex items-center gap-14px">
              <span className="title4 text-grey-black">차량 총 견적 금액</span>
              <span className="font-hsans-head text-34px font-medium leading-[44.2px] tracking-[-1.02px] text-grey-black">
                47,270,000원
              </span>
            </div>
          </div>

          {Object.values(userSelectedOptionData).map((optionGroup) => {
            const { title, options } = optionGroup as OptionGroupType;
            const totalPrice = Object.values(options).reduce(
              (sum, { price }) => sum + price!,
              0
            );

            return (
              <SummaryOptionsBox title={title} price={totalPrice} key={title}>
                {Object.values(options).map(({ type, name, price }, index) => {
                  const isDuplicatedType = title === '옵션' && index > 0;
                  return (
                    <SummaryOptionsBox.Option
                      type={isDuplicatedType ? '' : type}
                      name={name}
                      price={price!}
                      key={name}
                    />
                  );
                })}
              </SummaryOptionsBox>
            );
          })}
        </div>

        <div className="flex justify-between w-full">
          <Button size="xl" color="main-blue">
            구매 상담 신청
          </Button>
          <Button size="xl" color="white">
            시승 신청하기
          </Button>
          <Button size="xl" color="grey">
            공유하기
          </Button>
          <Button size="xl" color="grey">
            저장하기
          </Button>
        </div>

        <div className="flex flex-col w-full gap-60px">
          <div className="flex items-center justify-between h-68px px-24px">
            <span className="title3 text-grey-black">견적 자세히 보기</span>
            <div className="flex items-center gap-14px">
              <span className="title4 text-grey-black">차량 총 견적 금액</span>
              <span className="font-hsans-head text-34px font-medium leading-[44.2px] tracking-[-1.02px] text-[#36383C]">
                47,270,000원
              </span>
            </div>
          </div>

          <div className="flex flex-col gap-60px">
            {Object.values(userSelectedOptionData.mainOptions.options).map(
              (option) => (
                <DetailOptionBox option={option} key={option.name} />
              )
            )}
            {Object.values(userSelectedOptionData.colors.options).map(
              (option) => (
                <DetailOptionBox option={option} key={option.name} />
              )
            )}
            <DetailBasicOptionBox />
            <DetailSelectOptionBox />
          </div>
        </div>
      </div>
    </div>
  );
}

export default CompleteOptionPage;
