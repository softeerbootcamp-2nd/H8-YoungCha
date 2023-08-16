interface DescriptionProps {
  firstLine: string;
  secondLine: string;
}

function Description({ firstLine, secondLine }: DescriptionProps) {
  return (
    <div className="flex flex-wrap text-main-blue opacity-80 popup justify-center items-center mt-12px">
      <p>{firstLine}</p>
      <p>{secondLine}</p>
    </div>
  );
}

export default Description;
