import * as Icon from '@/assets/icons';
import { useState, useEffect } from 'react';
import Transition from '@/components/Transition/Transition.tsx';

const SHOW_LAUGHING_TIME = 500;

interface FeedbackCardProps {
  feedbackTitle: string;
  feedbackDescription: string;
}

function FeedbackCard({
  feedbackTitle,
  feedbackDescription,
}: FeedbackCardProps) {
  const [isGood, setIsGood] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => setIsGood(true), SHOW_LAUGHING_TIME);
    return () => {
      clearTimeout(timer);
      setIsGood(false);
    };
    // setIsGood(true);
  }, []);

  return (
    <Transition render from="opacity-0" to="opacity-100" unmount>
      <div
        className={`absolute w-full bg-main-blue border-2 border-main-blue rounded-4px p-20px top-0 left-0 bottom-0 right-0`}
      >
        <div className="flex">
          {isGood ? <Icon.SmileIcon /> : <Icon.LaughingFaceIcon />}
          {isGood && (
            <Icon.ThumbsUpIcon className="w-24px h-24px origin-bottom-left animate-good" />
          )}
        </div>

        <div className="font-medium text-white text-20px mt-10px">
          {feedbackTitle}
        </div>
        <div className="text-white body3 mt-6px">{feedbackDescription}</div>
      </div>
    </Transition>
  );
}

export default FeedbackCard;
