interface CenteredDescriptionProps {
  description: string;
}

function CenteredDescription({ description }: CenteredDescriptionProps) {
  return (
    <div className="flex flex-wrap text-main-blue opacity-80 popup justify-center items-center mt-12px">
      <p className="whitespace-pre-wrap text-center">{description}</p>
    </div>
  );
}

export default CenteredDescription;
