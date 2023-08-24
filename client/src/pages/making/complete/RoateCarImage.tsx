import useRotate from '@/hooks/useRotate';
import { MouseEventHandler, useLayoutEffect, useState } from 'react';

interface ImageRotatorProps {
  images: string[];
}

function RotateCarImage({ images }: ImageRotatorProps) {
  const { state, handleMouseDown, handleMouseMove, handleMouseUp } =
    useRotate();
  const preventEventDefault: MouseEventHandler<HTMLDivElement> = (e) =>
    e.preventDefault();

  const [isImageLoading, setIsImageLoading] = useState(true);

  useLayoutEffect(() => {
    let count = 0;
    setIsImageLoading(true);
    images.forEach((src) => {
      const image = new Image();
      image.src = src;
      image.onload = () => {
        count += 1;
        setIsImageLoading(false);
      };
      image.onerror = () => {
        count += 1;
        if (count === images.length) {
          setIsImageLoading(false);
        }
      };
    });
  }, [images]);

  return (
    <>
      {isImageLoading ? (
        <div className={`flex items-center justify-center`}>
          <svg
            className="animate-spin -ml-1 mr-3 h-5 w-5 text-black"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
          >
            <circle
              className="opacity-25"
              cx="12"
              cy="12"
              r="10"
              stroke="currentColor"
              strokeWidth="4"
            ></circle>
            <path
              className="opacity-75"
              fill="currentColor"
              d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
            ></path>
          </svg>
        </div>
      ) : (
        <div
          role="button"
          tabIndex={0}
          onMouseDown={handleMouseDown}
          onMouseMove={
            state.isMouseDown ? handleMouseMove : preventEventDefault
          }
          onMouseUp={handleMouseUp}
          onMouseLeave={handleMouseUp}
          className={`${
            state.isMouseDown ? 'cursor-grabbing' : 'cursor-grab'
          } relative w-full flex justify-center h-200px`}
        >
          <div className="relative">
            {images.map((imgSrc, idx) => (
              <img
                src={imgSrc}
                alt={imgSrc}
                key={idx}
                className={`${
                  idx === state.nextImgIdx ? 'block' : 'hidden'
                } pointer-events-none select-none`}
                width={400}
                height={200}
              />
            ))}
            <div className="absolute top-0 right-0 flex items-center justify-center text-white rounded-full opacity-30 body2 bg-grey-black w-40px h-40px">
              360°
            </div>
          </div>
        </div>
      )}
    </>
  );
}

export default RotateCarImage;
