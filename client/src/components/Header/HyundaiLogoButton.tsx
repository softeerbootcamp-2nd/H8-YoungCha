import { Link } from 'react-router-dom';
import * as Icon from '../../assets/icons';

function HyundaiLogoButton() {
  return (
    <Link to="/">
      <button className="w-149px h-21px pr-20px">
        <Icon.HyundaiLogo />
      </button>
    </Link>
  );
}

export default HyundaiLogoButton;
