import * as Icon from '@/assets/icons';
import { powerTrainMock } from '@/assets/mock/optionMock';
import { useState, useEffect } from 'react';

const SHOW_SMILE_TIME = 500;
const SHOW_LAUGHING_TIME = 1000;

interface FeedbackCardProps {
  isSelected: boolean;
}

function FeedbackCard({ isSelected }: FeedbackCardProps) {
  const [showSmileIcon, setShowSmileIcon] = useState(true);
  const [showLaughingIcon, setShowLaughingIcon] = useState(false);

  useEffect(() => {
    if (isSelected) {
      const timer1 = setTimeout(() => {
        setShowSmileIcon(false);
      }, SHOW_SMILE_TIME);

      const timer2 = setTimeout(() => {
        setShowLaughingIcon(true);
      }, SHOW_LAUGHING_TIME);

      return () => {
        clearTimeout(timer1);
        clearTimeout(timer2);
      };
    }
  }, [isSelected]);

  return (
    <div
      className={`bg-main-blue border-2 border-main-blue rounded-6px w-375px max-h-150px p-20px`}
    >
      <div
        className={`${
          showSmileIcon ? 'opacity-100' : 'opacity-0'
        } transition-opacity duration-500 linear`}
      >
        {showSmileIcon && <Icon.SmileIcon />}
      </div>
      <div
        className={`flex ${
          showLaughingIcon ? 'opacity-100' : 'opacity-0'
        } transition-opacity duration-500 linear`}
      >
        {showLaughingIcon && <Icon.LaughingFaceIcon />}
        {showLaughingIcon && <Icon.ThumbsUpIcon />}
      </div>

      <div className="font-medium text-white text-20px mt-10px">
        {powerTrainMock.feedback[0].title}
      </div>
      <div className="text-white body3 mt-6px">
        {powerTrainMock.feedback[0].description}
      </div>
    </div>
  );
}

export default FeedbackCard;
