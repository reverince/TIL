using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI;
using Windows.UI.Popups;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// 빈 페이지 항목 템플릿에 대한 설명은 https://go.microsoft.com/fwlink/?LinkId=402352&clcid=0x412에 나와 있습니다.

namespace HelloUWP
{
    /// <summary>
    /// 자체적으로 사용하거나 프레임 내에서 탐색할 수 있는 빈 페이지입니다.
    /// </summary>
    public sealed partial class DefaultPage : Page
    {
        private static int progress = 0;

        public DefaultPage()
        {
            this.InitializeComponent();

            createControl();
        }

        private void createControl()
        {
            // 버튼 생성
            Button btnDynamic = new Button();
            btnDynamic.Content = "TextBox 제거";
            btnDynamic.Click += BtnDynamic_Click;
            btnDynamic.Margin = new Thickness(0, 10, 0, 10);
            stk2.Children.Add(btnDynamic);

            // 리스트뷰 아이템 소스 설정
            string[] colors = { "빨강", "초록", "파랑" };
            listView1.ItemsSource = colors;
        }

        private void Grid_KeyDown(object sender, KeyRoutedEventArgs e)
        {
            switch (e.Key)
            {
                case Windows.System.VirtualKey.A:
                    progress = progress > progressBar1.Minimum ? progress - 1 : 100;
                    progressBar1.Value = progress;
                    break;
                case Windows.System.VirtualKey.D:
                    progress = progress < progressBar1.Maximum ? progress + 1 : 0;
                    progressBar1.Value = progress;
                    break;
            }
        }

        private void BtnDynamic_Click(object sender, RoutedEventArgs e)
        {
            Button btn = (Button)sender;

            stk2.Children.Remove((UIElement)this.FindName("txtBox1"));
            btn.Content = "제거했어요!";
        }

        private void BtnColorChoice_Click(object sender, RoutedEventArgs e)
        {
            CheckBox[] checkBoxes = new CheckBox[] { checkR, checkG, checkB };
            List<string> chosenColors = new List<string>();

            foreach (var c in checkBoxes)
                if (c.IsChecked == true)
                    chosenColors.Add((string)c.Content);
            txtColorResult.Text = chosenColors.Count > 0 ? $"선택한 색상: {String.Join(", ", chosenColors)}" : "색상을 하나 이상 골라주세요";
        }

        private void RadioButton_Checked(object sender, RoutedEventArgs e)
        {
            RadioButton radioButton = sender as RadioButton;
            string color = radioButton.Tag.ToString();
            switch (color)
            {
                case "빨강":
                    txtRadio.Foreground = new SolidColorBrush(Colors.Red);
                    break;
                case "초록":
                    txtRadio.Foreground = new SolidColorBrush(Colors.Green);
                    break;
                case "파랑":
                    txtRadio.Foreground = new SolidColorBrush(Colors.Blue);
                    break;
            }
        }

        private static int rptCount = 0;
        private void RptIncrease_Click(object sender, RoutedEventArgs e)
        {
            rptCount += 1;
            txtRepeat.Text = "rptCount: " + rptCount;
        }

        private void RptDecrease_Click(object sender, RoutedEventArgs e)
        {
            rptCount -= 1;
            txtRepeat.Text = "rptCount: " + rptCount;
        }

        private void Slider1_ValueChanged(object sender, RangeBaseValueChangedEventArgs e)
        {
            Slider slider = sender as Slider;
            if (slider != null)
            {
                txtSlider.FontSize = slider.Value;
            }
        }
        private void ToggleSwitch1_Toggled(object sender, RoutedEventArgs e)
        {
            ToggleSwitch toggleSwitch = sender as ToggleSwitch;
            if (toggleSwitch != null)
            {
                if (toggleSwitch.IsOn)
                    txtToggleSwitch.Visibility = Visibility.Visible;
                else
                    txtToggleSwitch.Visibility = Visibility.Collapsed;
            }
        }

        private void BtnProgressBar_Click(object sender, RoutedEventArgs e)
        {
            progress = progress < progressBar1.Maximum ? progress + 1 : 0;
            progressBar1.Value = progress;
        }

        private async void BtnMessageDialog_Click(object sender, RoutedEventArgs e)
        {
            string content = "MessageDialog";
            string title = "title";
            MessageDialog messageDialog = new MessageDialog(content, title);
            await messageDialog.ShowAsync();
        }

        private async void BtnContentDialog_Click(object sender, RoutedEventArgs e)
        {
            ContentDialog contentDialog = new ContentDialog()
            {
                Title = "Content Dialog Title",
                Content = "Are you sure?!",
                PrimaryButtonText = "YES"
            };
            await contentDialog.ShowAsync();
        }

        private async void ListView1_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            ListView listView = sender as ListView;
            string selected = listView.SelectedItem.ToString();
            MessageDialog messageDialog = new MessageDialog($"선택한 색상: {selected}");
            await messageDialog.ShowAsync();
        }

        private async void BtnSavePicker_Click(object sender, RoutedEventArgs e)
        {
            var savePicker = new Windows.Storage.Pickers.FileSavePicker();
            savePicker.SuggestedStartLocation =
                Windows.Storage.Pickers.PickerLocationId.DocumentsLibrary;
            // 디폴트 파일명
            savePicker.SuggestedFileName = "새로운 저장";
            // 파일 형식 드롭다운
            savePicker.FileTypeChoices.Add("텍스트", new List<string>() { ".txt" });

            Windows.Storage.StorageFile storageFile = await savePicker.PickSaveFileAsync();
            if (storageFile != null)
            {
                // 저장 중인 파일 접근 거부
                Windows.Storage.CachedFileManager.DeferUpdates(storageFile);
                // 저장
                await Windows.Storage.FileIO.WriteTextAsync(storageFile, txtBoxSave.Text);

                // 파일 저장 완료 상태 설정
                Windows.Storage.Provider.FileUpdateStatus status =
                    await Windows.Storage.CachedFileManager.CompleteUpdatesAsync(storageFile);

                if (status == Windows.Storage.Provider.FileUpdateStatus.Complete)
                {
                    txtSavePickerResult.Text = "저장 완료!";
                    FlyoutBase.ShowAttachedFlyout((FrameworkElement)sender);
                }
                else
                {
                    txtSavePickerResult.Text = "저장 실패...";
                }
            }
        }

        private async void BtnSave_Click(object sender, RoutedEventArgs e)
        {
            const string FILE_NAME = "save.txt";

            Windows.Storage.StorageFolder storageFolder = Windows.Storage.ApplicationData.Current.LocalFolder;
            Windows.Storage.StorageFile storageFile =
                await storageFolder.CreateFileAsync(FILE_NAME,
                Windows.Storage.CreationCollisionOption.OpenIfExists);
            await Windows.Storage.FileIO.WriteTextAsync(storageFile, txtBoxSave.Text);

            // 파일 저장 완료 상태 설정
            Windows.Storage.Provider.FileUpdateStatus status =
                await Windows.Storage.CachedFileManager.CompleteUpdatesAsync(storageFile);

            if (status == Windows.Storage.Provider.FileUpdateStatus.Complete)
            {
                txtSaveResult.Text = "저장 완료!";
            }
            else
            {
                txtSaveResult.Text = "저장 실패...";
            }
            FlyoutBase.ShowAttachedFlyout((FrameworkElement)sender);
        }

        private async void BtnLoad_Click(object sender, RoutedEventArgs e)
        {
            Windows.Storage.StorageFolder storageFolder = Windows.Storage.ApplicationData.Current.LocalFolder;
            const string FILE_NAME = "save.txt";

            if (await storageFolder.TryGetItemAsync(FILE_NAME) != null)
            {
                Windows.Storage.StorageFile storageFile =
                    await storageFolder.GetFileAsync(FILE_NAME);

                txtBoxSave.Text = await Windows.Storage.FileIO.ReadTextAsync(storageFile);
                txtLoadResult.Text = "불러오기 완료!";
            }
            else
            {
                txtLoadResult.Text = "불러오기 실패: 파일이 존재하지 않습니다.";
            }
            FlyoutBase.ShowAttachedFlyout((FrameworkElement)sender);
        }
    }
}
