import React, { memo, useRef } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import DraggableContainer from '@/components/DraggableContainer';

interface IProps {
  className?: string;
}

export default memo<IProps>(function Chart(props) {
  const { className } = props;

  const volatileRef = useRef<any>();

  return (
    <DraggableContainer
      volatileDom={{
        volatileRef,
        volatileIndex: 0,
      }}
      className={classnames(styles.box, className)}
    >
      <div ref={volatileRef} className={styles.box_left}>
        <div className={styles.box_left_title}>Dashboard</div>
      </div>
      <div></div>
    </DraggableContainer>
  );
});