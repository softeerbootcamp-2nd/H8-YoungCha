interface SingleDescriptionProps {
  singleLine: string;
}

function SingleDescription({ singleLine }: SingleDescriptionProps) {
  return (
    <div className="flex flex-wrap text-main-blue opacity-80 popup justify-center items-center mt-12px">
      <p>{singleLine}</p>
    </div>
  );
}

export default SingleDescription;
