import { useContext, useEffect, useRef, useState } from 'react';
import getOptionGroupsTotalPrice from '@/utils/getTotalPrice.ts';
import { UserSelectedOptionDataContext } from '@/store/useUserSelectedOptionContext';

function easeOutExpo(t: number) {
  return t === 1 ? 1 : 1 - Math.pow(2, -10 * t);
}

const DURATION = 1000;
const FRAME_RATE = 1000 / 60;

function useAnimatedPrice() {
  const { userSelectedOptionData } = useContext(UserSelectedOptionDataContext);

  const price = Object.values(userSelectedOptionData).reduce(
    (sum, { options }) => getOptionGroupsTotalPrice(options) + sum,
    0
  );

  const [totalPrice, setTotalPrice] = useState(price);
  const [count, setCount] = useState(totalPrice);

  const totalFrame = Math.round(DURATION / FRAME_RATE);

  const prevCount = useRef(0);

  useEffect(() => {
    setTotalPrice(price);
  }, [userSelectedOptionData]);

  useEffect(() => {
    let currentNumber = 0;
    prevCount.current = count;
    const counter = setInterval(() => {
      const progress = easeOutExpo(++currentNumber / totalFrame);
      setCount(
        totalPrice > prevCount.current
          ? Math.round(
              prevCount.current + (totalPrice - prevCount.current) * progress
            )
          : Math.round(
              prevCount.current - (prevCount.current - totalPrice) * progress
            )
      );
      // prevCount.current = count;
      if (progress === 1) {
        clearInterval(counter);
      }
    }, FRAME_RATE);

    return () => clearInterval(counter);
  }, [totalPrice, totalFrame]);

  return count;
}

export default useAnimatedPrice;
