clf;
t = 0 : 0.01 : 3*pi;
y1 = 2 * sin(t);
y2 = 2 * sin(t-2);
subplot(2, 1, 1);
plot(t, y1);
title('y1(t) �׷���');
subplot(2, 1, 2);
plot(t, y2);
title('y2(t) �׷���');