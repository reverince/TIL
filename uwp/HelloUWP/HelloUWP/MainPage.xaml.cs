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
    public sealed partial class MainPage : Page
    {
        public MainPage()
        {
            this.InitializeComponent();

            createControl();

            // 리스트뷰 아이템 소스 설정
            string[] colors = { "Red", "Green", "Blue" };
            listView1.ItemsSource = colors;
        }

        private void createControl()
        {
            Button btnDynamic = new Button();
            btnDynamic.Content = "Remove the TextBox";
            btnDynamic.Click += BtnDynamic_Click;
            btnDynamic.Margin = new Thickness(0, 10, 0, 10);
            stk2.Children.Add(btnDynamic);
        }

        private void BtnDynamic_Click(object sender, RoutedEventArgs e)
        {
            Button btn = (Button)sender;
            btn.Content = "Nice!";
            stk2.Children.Remove((UIElement)this.FindName("txtBox1"));
        }
        
        private void BtnColorChoice_Click(object sender, RoutedEventArgs e)
        {
            txtColorResult.Text = "You've chosen: ";
            string strColors = string.Empty;
            CheckBox[] checkBoxes = new CheckBox[] { checkR, checkG, checkB };
            foreach (var c in checkBoxes)
            {
                if (c.IsChecked == true)
                    strColors += c.Content;
            }
            txtColorResult.Text += strColors;
        }

        private void RadioButton_Checked(object sender, RoutedEventArgs e)
        {
            RadioButton radioButton = sender as RadioButton;
            if (radioButton != null)
            {
                string color = radioButton.Tag.ToString();
                if (color == "Red")
                    txtRadio.Foreground = new SolidColorBrush(Colors.Red);
                else if (color == "Green")
                    txtRadio.Foreground = new SolidColorBrush(Colors.Green);
                else if (color == "Blue")
                    txtRadio.Foreground = new SolidColorBrush(Colors.Blue);
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

        private async void BtnMessageDialog_Click(object sender, RoutedEventArgs e)
        {
            string msg = "Message Dialog";
            string title = "Dialog Title";
            MessageDialog messageDialog = new MessageDialog(msg, title);
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
            MessageDialog messageDialog = new MessageDialog($"Color: {selected}");
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
